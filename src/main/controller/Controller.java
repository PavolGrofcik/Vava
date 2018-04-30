package main.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mail.sender.Test;
import main.entities.Account;
import main.entities.Customer;


/**
 * Trieda Controller obsahuje hlavné metódy pre prácu s DB a perzistenciou JPA
 * Používa sa návrhový vzor Singleton
 * @author grofc
 *
 */

public class Controller {
	
	private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
	private static final String PATH = "src/logfile.txt";
	
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
				
			LOGGER.setLevel(Level.WARNING);		//Logger global level
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

	/*public int sendNewPassword(TextField username, TextField answer, TextField question) {
		
		
	}
	*/
	
	

	public int loginCustomer(TextField username, PasswordField password) throws NoSuchAlgorithmException {
		LOGGER.entering(this.getClass().getName(), "loginCustomer");
		
		Session session = factory.openSession();
	
		if(!checkInputs(username.getText().toString(), password.getText().toString())) {
			return -1;	// Missing values
		}
		
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
		
		return 1;
	}
	
	
	@SuppressWarnings("unchecked")
	public int sendNewPassword(TextField email, PasswordField answer, PasswordField password) {
		LOGGER.entering(this.getClass().getName(), "sendNewPassword");
		
		Session session = factory.openSession();
		
		if(!checkInput(email.getText(), answer.getText(), password.getText())){
			return -1;	//	Missing values
		}
		
		Transaction transaction = session.beginTransaction();
		
		Account account;
		Customer customer;
		
		try {
		
			TypedQuery<Customer> query = session.createQuery("SELECT new Customer(id, firstName, lastName, " +
					"sex, telNumber, email) FROM Customer WHERE email = :arg");
			query.setParameter("arg", email.getText());
			
			if(query.getResultList().isEmpty()) {
				return -2;	//	Customer with given email does not exist
			}
			
			customer = query.getResultList().get(0);
			
			TypedQuery<Account> query2 = session.createQuery("SELECT new Account(id, customerId, controlQuestionId, " +
					"cash, username, password, answer) FROM Account WHERE customerId = :arg");
			query2.setParameter("arg", customer.getId());
			
			if(query2.getResultList().isEmpty()) {
				return -2;	//	Customer does not have created account
			}
			
			account = query2.getResultList().get(0);
			
			if(!account.getAnswer().equals(answer.getText())) {
				return -3;	//	Invalid secret answer
			}
			
			String newPassword = passwordHashing(password.getText());
			account.setPassword(newPassword);
			
			Test.sendGmailMessage(customer.getEmail(), customer.getFirstName(), password.getText().toCharArray());
			
			session.saveOrUpdate(account);
			
			transaction.commit();
			
		} catch (HibernateException e) {
			LOGGER.log(Level.SEVERE, "Hibernate exception", e);
			transaction.rollback();
			return -4;
		}finally {
			session.close();
		}
		
		return 1;
	}
	
	
	private String passwordHashing(String password) {
		LOGGER.entering(this.getClass().getName(), "passwordHashing");
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.log(Level.SEVERE, "Hash algorithm ex", e);
			return null;
		}
        
        byte[] messageDigest = md.digest(password.getBytes());
        
        BigInteger number = new BigInteger(1, messageDigest);
        String hashtext = number.toString(16);
        
        return hashtext;
	}
	
	
	private boolean checkInputs(String username, String password) {
		if(username.isEmpty()|| password.isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	/*private boolean checkInputs(String s, String s2, String s3) {
		
	}*/
	
	private boolean checkInput(String email, String answer, String password) {
		if(email.isEmpty() || answer.isEmpty() || password.isEmpty()) {
			return false;
		}
		
		return true;
	}

}
