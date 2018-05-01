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
import javax.persistence.criteria.CriteriaBuilder.Case;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

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
	
	//Personal data of current customer
	private int accountID;			// Login -> get accountId
	private int customerID;			//	Login -> get customerId
	
	
	static {
		try {
			FileHandler handler = new FileHandler(PATH);

			InputStream configFile = Controller.class.getResourceAsStream("p.properties");
			LogManager.getLogManager().readConfiguration(configFile);
			handler.setFormatter(new SimpleFormatter());
			
			LOGGER.addHandler(handler);
			LOGGER.setUseParentHandlers(false);		//	Logovanie nebude na konzole
			LOGGER.log(Level.INFO, "info");	
			LOGGER.setLevel(Level.INFO);			//	Logger global level
			
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
	
	public int changeAccountSettings(PasswordField passwdField, PasswordField confirmField,
			TextField telField, TextField emailField) {
		
		LOGGER.entering(this.getClass().getName(), "changeAccountSettings");
		
		int status = selectDataToUpdate(passwdField.getText(), confirmField.getText(),
				emailField.getText(), telField.getText());
		
		LOGGER.log(Level.INFO, "Status selected "+ status);
		
		int retVal = -1;;
		
		if (status <= 0) {
			retVal = -1; // Missing values
		} else if (status <= 2) {
			retVal = changeData(emailField.getText(), telField.getText(), status);
		} else if (status == 3) { // Both email & telNumber
			changeData(emailField.getText(), telField.getText(), 1);
			retVal = changeData(emailField.getText(), telField.getText(), 2);
		} else if (status == 4) { // Passwords only
			boolean match = verifyStrings(passwdField.getText(), confirmField.getText());
			LOGGER.log(Level.INFO, "Passwords matchings status " + match);

			if (match == false) {
				retVal = -2; // Passwords are not the same
			} else {
				retVal = changePassword(passwdField.getText(), confirmField.getText());
			}
		} else if (status >= 5) {

			// status = 5 email
			// status = 6 telNumber
			// status = 7 All
			boolean match = verifyStrings(passwdField.getText(), confirmField.getText());

			LOGGER.log(Level.INFO, "Passwords matchings status " + match);

			if (match == false) {
				retVal = -2; // Passwords are not the same
			} else {
				switch (status) {
				case 5:
					changeData(emailField.getText(), telField.getText(), 2);
					retVal = changePassword(passwdField.getText(), confirmField.getText());
					break;
				case 6:
					changeData(emailField.getText(), telField.getText(), 1);
					retVal = changePassword(passwdField.getText(), confirmField.getText());
					break;
				
				case 7:
					changeData(emailField.getText(), telField.getText(), 1);
					changeData(emailField.getText(), telField.getText(), 2);
					retVal = changePassword(passwdField.getText(), confirmField.getText());
					break;
				}
			}
		}
		
		LOGGER.log(Level.INFO, "Returnig value is " + retVal);
		LOGGER.exiting(this.getClass().getName(), "changeAccountSettings");
		
		return retVal;
	}
	
	@SuppressWarnings("unused")
	public String getQuestionByUsername(TextField username) {
		LOGGER.entering(this.getClass().getName(), "getQuestionByUsername");

		if(!checkInput(username.getText())) {
			return null;
		}
		
		Session session = factory.openSession();
		Account account;
		ControlQuestion question;
		String result = "";
		
		try {
			Transaction transaction = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			/*TypedQuery<Account> query = session.createQuery("SELECT new Account(id,customerId, "
					+ "controlQuestionId, username, password, answer) FROM ControlQuestion WHERE "
					+ "username = :arg");*/
			TypedQuery<Account> query = session.createQuery("SELECT new Account(controlQuestionId, "
					+ "username, password) FROM Account WHERE username = :arg");
			
			query.setParameter("arg", username.getText());
			
			try {
				if(query.getResultList().isEmpty()) {
					LOGGER.log(Level.WARNING, "Query not found");
					return null;		// Username does not exist
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Query result list", e);
				return null;
			}
			
			account = query.getResultList().get(0);
			LOGGER.log(Level.INFO, "Account cQId " + account.getControlQuestionId());

			question = session.get(ControlQuestion.class, account.getControlQuestionId());

			LOGGER.log(Level.INFO, "Control Question " + question.getQuestion());
			
			if(question == null) {
				return null;
			}
			
			result = question.getQuestion();
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
			
			if(!verifyStrings(account.getAnswer(), answer.getText())) {
				return -2;	//Do not match
			}
			
			customer = session.get(Customer.class, account.getCustomerId());
			
			LOGGER.log(Level.INFO, "Customer gmail " + customer.getEmail());
			String tmp = PasswdGenerator.generate();

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
			TypedQuery<Account> query = session.createQuery("SELECT new Account(id, customerId, controlQuestionId, " 
					+ "cash, username, password, answer) FROM Account WHERE username = :arg");
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

			LOGGER.log(Level.CONFIG, "Account ID of logged user" + account.getId());
			LOGGER.log(Level.CONFIG, "Customer ID of logged user" + account.getCustomerId());
			
			customerID = account.getCustomerId();
			accountID = account.getId();
			
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
	
	
	private int changePassword(String password, String confirmPass) {
		LOGGER.entering(this.getClass().getName(), "changePasswords");	
		Session session = factory.openSession();
		Transaction transaction = null;
		
		Account account;
		
		try {
			transaction = session.beginTransaction();
			
			try {
				account = session.get(Account.class, accountID);	
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Session account get", e);
				return -3;
			}
			
			LOGGER.log(Level.INFO, "Hashed Password" + passwordHashing(confirmPass));
			account.setPassword(passwordHashing(confirmPass));
			
			transaction.commit();		
		} catch (HibernateException e) {
			LOGGER.log(Level.SEVERE, "Hibernate Exception", e);
			transaction.rollback();
			return -3;
		}finally {
			session.close();
		}
		
		LOGGER.exiting(Controller.class.getName(), "changePasswords");
		return 1;
	}
	
	//Email or TelNumber
	private int changeData(String email, String telNumber, int status) {
		LOGGER.entering(this.getClass().getName(), "changeData");	
		Session session = factory.openSession();
		Transaction transaction = null;
		
		Customer customer;
		
		try {
			transaction=session.beginTransaction();
			
			try {
				customer = session.get(Customer.class, customerID);	
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Session customer get", e);
				return -3;
			}
			
			switch (status) {
			case 1: // Tel Number
				customer.setTelNumber(telNumber);
				break;
			case 2: // Email
				customer.setEmail(email);
				break;
			}
			
			transaction.commit();
		} catch (HibernateException e) {
			LOGGER.log(Level.SEVERE, "Hibernate Exception", e);
			transaction.rollback();
			return -3;
		}finally {
			session.close();
		}
		
		LOGGER.exiting(Controller.class.getName(), "changeData");
		return 1;
	}
	
	
	private int selectDataToUpdate(String passwd, String confirmPasswd, String email,
			String telNumber) {
		
		if (passwd.isEmpty() && confirmPasswd.isEmpty() && email.isEmpty()
				&& telNumber.isEmpty()) {	// All fields are empty
			return 0;
		}else if (passwd.isEmpty() && confirmPasswd.isEmpty() && email.isEmpty() 
				&& !telNumber.isEmpty()) {	// Tel Number is not 
			return 1;			
		}else if (passwd.isEmpty() && confirmPasswd.isEmpty() && !email.isEmpty() 
				&& telNumber.isEmpty()) {	// Email is not
			return 2;
		}else if (passwd.isEmpty() && confirmPasswd.isEmpty() && !email.isEmpty() 
				&& !telNumber.isEmpty()) {	// Email & Tel number are not
			return 3;
		}else if (!passwd.isEmpty() && !confirmPasswd.isEmpty()) {
			
			if(telNumber.isEmpty() && email.isEmpty()) {			//	Only passwords are not
				return 4;
			}else if(telNumber.isEmpty() && !email.isEmpty()) {		//	Passwords & email are not
				return 5;
			}else if (!telNumber.isEmpty() && email.isEmpty()) {	// Passwords & Tel Number are not
				return 6;	
			}
			}else {		//	All are filled
				return 7;	
			}
		
		return -1; //Error encountered
	}
	
	private boolean verifyStrings(String answer, String answerDB) {
		LOGGER.entering(this.getClass().getName(), "verifyStrings");
		
		try {
			if(answer.equals(answerDB)) {
				LOGGER.log(Level.INFO, "Strings are " + answer + " " + answerDB);
				return true;
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Comparing Strings", e);
			return false;
		}
		
		LOGGER.exiting(Controller.class.getName(), "verifyStrings");
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
