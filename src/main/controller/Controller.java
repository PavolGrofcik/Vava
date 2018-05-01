package main.controller;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mail.sender.Sender;
import main.entities.Account;
import main.entities.ControlQuestion;
import main.entities.Customer;


/**
 * Trieda Controller obsahuje hlavn� met�dy pre pr�cu s DB a perzistenciou JPA
 * Pou��va sa n�vrhov� vzor Singleton
 * @author grofc
 *
 */

public class Controller {
	
	private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
	private static final String PATH = "Logger/logfile.txt";
	
	private SessionFactory factory;
	private static Controller controller = new Controller();
	
	
	static {
		try {
			//Handler handler = new FileHandler("test%u.%g.txt");
			//handler.setFormatter(new SimpleFormatter());
			//handler.setEncoding("UTF-8");
			FileHandler handler = new FileHandler(PATH);
			//FileInputStream stream = new FileInputStream("src/resources/p.properties");
			InputStream configFile = Controller.class.getResourceAsStream("p.properties");
			//LogManager.getLogManager().readConfiguration(stream);
			LogManager.getLogManager().readConfiguration(configFile);
			handler.setFormatter(new SimpleFormatter());
			
			LOGGER.addHandler(handler);
			LOGGER.setUseParentHandlers(false);		//Logovanie nebude na konzole
			LOGGER.log(Level.INFO, "info");	
			LOGGER.setLevel(Level.INFO);		//Logger global level
			
		} catch (SecurityException e) {
			LOGGER.log(Level.CONFIG, "Log config", e);
		} catch (IOException e) {
			LOGGER.log(Level.CONFIG, "IOexception", e);
		}
		System.out.println("Log level is: " + LOGGER.getLevel());
	}
	
	private Controller() {
		this.factory = HibernateUtil.getSessionFactory();
	}

	public static Controller getInstance() {		
		return controller;
	}
	
	public void shutDown() {
		factory.close();
	}
	
	public String getQuestionByUsername(TextField username) {
		LOGGER.entering(this.getClass().getName(), "getQuestionByUsername");
		
		if(checkInput(username.getText())) {
			return null;
		}
		
		Session session = factory.openSession();
		Account account;
		ControlQuestion question;
		String result = "";
		
		try {
			Transaction transaction = session.beginTransaction();
			
			TypedQuery<Account> query = session.createQuery("SELECT new Account(id,customerId, "
					+ "controlQuestionId, username, password, answer) FROM ControlQuestion WHERE "
					+ "username = :arg");
			query.setParameter("arg", username.getText());
			
			try {
				if(query.getResultList().isEmpty()) {
					return null;		// Username does not exist
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Query result list", e);
				return null;
			}
			
			account = query.getResultList().get(0);
			LOGGER.log(Level.FINE, "Account cQId", account.getControlQuestionId());
			
			question = session.get(ControlQuestion.class, account.getControlQuestionId());
			
			LOGGER.log(Level.FINE, "Contorl Question", question.getQuestion());
			result= question.getQuestion();
			
			transaction.commit();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Hibernate ex", e);
		}finally {
			session.close();
		}
		
		LOGGER.exiting(Controller.class.getName(), "getQuestionByUsername");
		return result;
	}

	@SuppressWarnings("unchecked")
	public int sendNewPassword(TextField username, TextField answer, TextField question) {
		LOGGER.entering(this.getClass().getName(), "sendNewPassword");
		
		Session session = factory.openSession();
		
		if(!checkInput(username.getText(), answer.getText(), question.getText())) {
			return -1;
		}
		
		String user = username.getText();
		
		Account account;
		ControlQuestion cq;
		Customer customer;
		
		try {
			Transaction transaction = session.beginTransaction();
			
			TypedQuery<ControlQuestion> query = session.createQuery(
					"SELECT new ControlQuestion(id, question) " + "FROM ControlQuestion WHERE question = :arg");
			query.setParameter("arg", question.getText());
			
			try {
				if(query.getResultList().isEmpty()) {
					return - 2;		// Username does not exist
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Query result list", e);
				return -3;
			}

			cq=query.getResultList().get(0);
			
			Query query2 = session.createQuery("SELECT new Account(id, customerId, "
					+ "controlQuestionId, username, password, answer) "
					+ "FROM Account WHERE controlQuestionId = :arg AND username = :arg2");
			query2.setParameter("arg", 	cq.getId());
			query2.setParameter("arg2", user);
			
			try {
				account = (Account) query2.getResultList().get(0);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Query2 result list", e);
				return -3;
			}
			
			if(!verifyAnswer(account.getAnswer(), answer.getText())) {
				return -2;	//Do not match
			}
			
			customer = session.get(Customer.class, account.getCustomerId());
			String tmp = PasswdGenerator.generate();

			System.out.println("Your new passwd is " + tmp);

			String newPassword = passwordHashing(tmp);
			account.setPassword(newPassword);
		
		
			new Thread(() -> {	// Odoslanie mailu zákazníkovi
				Sender.sendGmailMessage(customer.getEmail(), account.getUsername(), tmp);
			}).start();
			
			
			session.saveOrUpdate(account);
			transaction.commit();
			
		} catch (HibernateException e) {
			LOGGER.log(Level.SEVERE, "Hibernate Ex", e);
			return -3;
		}finally {
			session.close();
		}
		
		LOGGER.exiting(Controller.class.getName(), "sendNewPassword");
		
		return 1;
	}
	

	public int loginCustomer(TextField username, PasswordField password) throws NoSuchAlgorithmException {
		LOGGER.entering(this.getClass().getName(), "loginCustomer");
		
	
		if(!checkInputs(username.getText().toString(), password.getText().toString())) {
			return -1;	// Missing values
		}
		
		Session session = factory.openSession();
		
		String usernme = username.getText();
		Account account;
		
		Transaction transaction = session.beginTransaction();
	
		try {
			
			@SuppressWarnings("unchecked")
			TypedQuery<Account> query = session.createQuery("SELECT new Account(customerId, controlQuestionId, " +
					"cash, username,password,answer) FROM Account WHERE username = :arg");
			query.setParameter("arg", usernme);
			
			if(query.getResultList().isEmpty()) {
				return - 2;		// Username does not exist
			}
			
			account = query.getResultList().get(0);
			
	        String hashtext = passwordHashing(password.getText());
	        
			try {
				if(!hashtext.equals(account.getPassword())) {
					
					return -2;		// Invalid password
				}
				
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Hash ex", e);
			}
			
			transaction.commit();
			
		} catch (HibernateException e) {
			LOGGER.log(Level.SEVERE, "Hibernate Ex", e);

			transaction.rollback();
			return -3;
			
		}finally {
			session.close();
		}
		LOGGER.exiting(Controller.class.getName(), "loginCustomer");
		return 1;
	}
	
	
	
	private String passwordHashing(String password) {
		LOGGER.entering(this.getClass().getName(), "passwordHashing");

		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.log(Level.SEVERE, "Hash algorithm ex", e);
			return null;
		}

		byte[] messageDigest = md.digest(password.getBytes());

		BigInteger number = new BigInteger(1, messageDigest);
		String hashtext = number.toString(16);

		LOGGER.exiting(Controller.class.getName(), "passwordHashing");

		return hashtext;
	}
	
	private boolean verifyAnswer(String answer, String answerDB) {
		LOGGER.entering(this.getClass().getName(), "verifyAnswer");
		
		System.out.println("String are " + answer + " DB " + answerDB);
		
		try {
			if(answer.equals(answerDB)) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Comparing Strings", e);
			return false;
		}
		
		LOGGER.exiting(Controller.class.getName(), "verifyAnswer");
		return false;
	}
	
	
	private boolean checkInputs(String username, String password) {
		if(username.isEmpty()|| password.isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkInput(String username) {
		if(username.isEmpty()) {
			return false;
		}
		return true;
	}
	
	
	private boolean checkInput(String email, String answer, String password) {
		if(email.isEmpty() || answer.isEmpty() || password.isEmpty()) {
			return false;
		}
		
		return true;
	}

}
