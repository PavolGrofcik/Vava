package main.controller;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
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


import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mail.sender.Sender;
import main.entities.Account;
import main.entities.ControlQuestion;
import main.entities.Customer;
import qrcode.generator.QrGenerator;

/**
 * Controller je hlavná trieda zodpovedná za aplikačnú
 * logiku, rovnako aj prácu s DB
 * @author grofc
 *
 */

public class Controller {
	
	private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
	private static final String PATH = "Logger/logfile.txt";
	
	private static final int PASSWORD_LENGTH = 8;
	private static final int TEL_NUMBER_LENGTH = 13;
	private static final int MIN_DATE_OF_BIRTH = 2000;
	
	private SessionFactory factory;
	private static Controller controller = new Controller();
	
	private int accountID;
	private int customerID;			
	
	static {
		try {
			FileHandler handler = new FileHandler(PATH);
			InputStream configFile = Controller.class.getResourceAsStream("p.properties");
			LogManager.getLogManager().readConfiguration(configFile);
			handler.setFormatter(new SimpleFormatter());
			
			LOGGER.addHandler(handler);
			LOGGER.setUseParentHandlers(false);
			LOGGER.setLevel(Level.INFO);
			
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
	
	public int registrateCustomer(TextField name, TextField surname, DatePicker birth, TextField telNumber,
			TextField city, TextField email, TextField address, CheckBox female, CheckBox male, TextField username,
			PasswordField password, PasswordField confirm, TextField answer, ComboBox<String> question) {

		LOGGER.entering(this.getClass().getName(), "registrateCustomer");

		int status = InputController.verifyCustomerInput(name, surname, birth, male, female, telNumber, city, email,
				address);

		int accountStatus = InputController.verifyAccountInput(username, password, confirm, answer, question);
		LOGGER.log(Level.INFO, "Input status registration: " + status + " Account status" + accountStatus);

		boolean sexCheckBox = false;
		int customerID;
		int controlQuestionId = -1;

		if (status == 1 && accountStatus == 1) {
			
			LOGGER.log(Level.INFO, "Verify registration data status:  " + verifyRegistrationData(birth, telNumber));
			LOGGER.log(Level.INFO, "Verify Passwords status: " + verifyPasswords(password, confirm));
			
			if(!verifyPasswords(password, confirm)) {
				return 0;
			}
			
			if(!verifyRegistrationData(birth, telNumber)) {
				return -2;
			}
			
			if (verifyRegistrationData(birth, telNumber) == true && verifyPasswords(password, confirm) == true) {
				sexCheckBox = male.isSelected();
				controlQuestionId = getQuestionID(question.getValue());
				
				LOGGER.log(Level.INFO, "Question is: " + question.getValue());
				LOGGER.log(Level.INFO, "Control question ID: " + controlQuestionId);
				
				if(!verifyTelNumber(telNumber)) {
					return -4;
				}

				if (controlQuestionId == -1 || verifyUniqueUsername(username.getText()) != 1) {
					LOGGER.log(Level.WARNING, "Username already exists");
					return -3;
				}
			}
		} else {
			return -1;
		}
		
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			Customer customer = new Customer(name.getText(), surname.getText(), birth.getValue(),
					sexCheckBox == true ? 'M' : 'F', telNumber.getText(), city.getText(), address.getText(),
					email.getText());
			session.save(customer);

			customerID = customer.getId();
			LOGGER.log(Level.INFO, "New Customer ID: " + customerID);

			Account account = new Account(customerID, username.getText(), controlQuestionId,
					passwordHashing(password.getText()), answer.getText());
			session.save(account);

			new Thread(() -> {
				QrGenerator.createQRCode(account.getId(), account.getUsername());
			}).start();

			new Thread(() -> {
				Sender.generateAndSendEmail(customer.getEmail());
			}).start();

			transaction.commit();
		} catch (HibernateException e) {
			LOGGER.log(Level.SEVERE, "Hibernate Exception", e);
			transaction.rollback();
			return 0;
		} finally {
			session.close();
		}

		LOGGER.exiting(this.getClass().getName(), "registrateCustomer");
		return 1;
	}
	
	public int changeAccountSettings(PasswordField oldField, PasswordField passwdField, 
			PasswordField confirmField, TextField telField, TextField emailField) {
		
		LOGGER.entering(this.getClass().getName(), "changeAccountSettings");
		
		int status = selectDataToUpdate(passwdField.getText(), confirmField.getText(),
				emailField.getText(), telField.getText());
		
		LOGGER.log(Level.INFO, "Status selected "+ status);
		
		int retVal = -1;
		
		if (status <= 0) {
			retVal = -1; // Missing values
		} else if (status <= 2) {
			retVal = changeData(emailField.getText(), telField.getText(), status);
		} else if (status == 3) { // Both email & telNumber
			changeData(emailField.getText(), telField.getText(), 1);
			retVal = changeData(emailField.getText(), telField.getText(), 2);
		} else if (status == 4) { // Passwords only
			if (oldField.getText().isEmpty() || confirmField.getText().length() < PASSWORD_LENGTH) {
				retVal = -2;
			} else {

				boolean match = verifyAnswers(passwdField.getText(), confirmField.getText());
				LOGGER.log(Level.INFO, "Passwords matchings status " + match);

				if (match == false) {
					retVal = -2; // Passwords are not the same
				} else {	
					if(checkPassword(oldField.getText())) {
						retVal = changePassword(passwdField.getText(), confirmField.getText());	
					}else {
						LOGGER.log(Level.WARNING, "Incorrect passwords");
						retVal = -2;
					}
				}
			}
		} else if (status >= 5) {
			// status = 5 + email
			// status = 6 + telNumber
			// status = 7 All
			boolean match = verifyAnswers(passwdField.getText(), confirmField.getText());

			LOGGER.log(Level.INFO, "Passwords matchings status " + match);

			if (match == false) {
				retVal = -2; // Passwords are not the same
				switch (status) {
				case 5:
					changeData(emailField.getText(), telField.getText(), 2);
					break;
				case 6:
					changeData(emailField.getText(), telField.getText(), 1);
					break;
				case 7:
					changeData(emailField.getText(), telField.getText(), 1);
					changeData(emailField.getText(), telField.getText(), 2);
					break;
				}
			} else {
				if(checkPassword(oldField.getText()) && confirmField.getText().length() >= PASSWORD_LENGTH) {
					retVal = changePassword(passwdField.getText(), confirmField.getText());	
				}else {
					retVal = -2;
				}
				
				switch (status) {
				case 5:
					changeData(emailField.getText(), telField.getText(), 2);
					break;
				case 6:
					changeData(emailField.getText(), telField.getText(), 1);
					break;
				case 7:
					changeData(emailField.getText(), telField.getText(), 1);
					changeData(emailField.getText(), telField.getText(), 2);
					break;
				}
			}
		}
		
		LOGGER.log(Level.INFO, "Returning value is " + retVal);
		LOGGER.exiting(this.getClass().getName(), "changeAccountSettings");
		
		return retVal;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
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
			
			TypedQuery<Account> query = session.createQuery("SELECT new Account(controlQuestionId, "
					+ "username, password) FROM Account WHERE username = :arg");
			query.setParameter("arg", username.getText());

			try {
				if(query.getResultList().isEmpty()) {
					LOGGER.log(Level.WARNING, "Username not found");
					return null;
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

		if (!checkInput(username.getText(), answer.getText(), question.getText())) {
			return -1;
		}

		String user = username.getText();

		Account account;
		ControlQuestion cq;
		Customer customer;

		try {
			Transaction transaction = session.beginTransaction();

			TypedQuery<ControlQuestion> query = session
					.createQuery("SELECT new ControlQuestion(id, question) FROM ControlQuestion WHERE question = :arg");
			query.setParameter("arg", question.getText());

			try {
				if (query.getResultList().isEmpty()) {
					return -2; // Username does not exist
				}
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Query result list", e);
				return -3;
			}

			cq = query.getResultList().get(0);

			Query query2 = session.createQuery(
					"SELECT new Account(id, customerId, " + "controlQuestionId, username, password, answer) "
							+ "FROM Account WHERE controlQuestionId = :arg AND username = :arg2");
			query2.setParameter("arg", cq.getId());
			query2.setParameter("arg2", user);

			try {
				account = (Account) query2.getResultList().get(0);
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Query2 result list", e);
				return -3;
			}

			if (!verifyAnswers(account.getAnswer(), answer.getText())) {
				return -2; // Do not match
			}

			customer = session.get(Customer.class, account.getCustomerId());

			LOGGER.log(Level.INFO, "Customer gmail " + customer.getEmail());
			String tmp = PasswdGenerator.generate();

			String newPassword = passwordHashing(tmp);
			account.setPassword(newPassword);

			new Thread(() -> { // Send an email to customer
				Sender.sendGmailMessage(customer.getEmail(), account.getUsername(), tmp);
			}).start();

			session.saveOrUpdate(account);
			transaction.commit();

		} catch (HibernateException e) {
			LOGGER.log(Level.SEVERE, "Hibernate Ex", e);
			return -3;
		} finally {
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
	        LOGGER.log(Level.INFO, "Hashed passwd: " + hashtext);
	        
			try {
				if(!hashtext.equals(account.getPassword())) {	
					return -2;		// Invalid password
				}
				
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "Incorrect passwd", e);
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
	
	@SuppressWarnings("unchecked")
	public java.util.List<String> getControlQuestions(){
		Session session = factory.openSession();
		Transaction transaction = null;
		
		java.util.List<String> questions = null;
		
		try {
			transaction = session.beginTransaction();
			Query query;
			query = session.createQuery("SELECT question FROM ControlQuestion");
			
			try {
				if(!query.getResultList().isEmpty()) {
					questions = query.getResultList();
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Query failed", e);
				return null;
			}
			
			transaction.commit();
		} catch (HibernateException e) {
			LOGGER.log(Level.SEVERE, "Hibernate Exceptoin", e);
			transaction.rollback();
		}finally {
			session.close();
		}
		
		return questions;
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
				LOGGER.log(Level.SEVERE, "Session account get failed", e);
				return -3;
			}
			
			LOGGER.log(Level.INFO, "Hashed Password is: " + passwordHashing(confirmPass));
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
	
	private int getQuestionID(String question) {
		LOGGER.entering(this.getClass().getName(), "getQuestionID");
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		int question_id = -1;
		
		try {
			Query query = session.createQuery("SELECT id FROM ControlQuestion WHERE question = :arg");
			query.setParameter("arg", question);
			
			if(query.getResultList().isEmpty()) {
				LOGGER.log(Level.WARNING, "Question not found");
				return question_id;
			}
			
			question_id = (int) query.getResultList().get(0);

		} catch (HibernateException e) {
			transaction.rollback();
			LOGGER.log(Level.SEVERE, "Hibernate Exception", e);
			return question_id;
		}finally {
			session.close();
		}
		
		LOGGER.exiting(this.getClass().getName(), "getQuestionID");
		return question_id;
	}
	
	private int verifyUniqueUsername(String username) {
		LOGGER.entering(this.getClass().getName(), "verifyUniqueUsername");
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		
		int status = -1;
		try {
			Query query = session.createQuery("SELECT id FROM Account WHERE username = :arg");
			query.setParameter("arg", username);
			
			if(query.getResultList().isEmpty()) {
				status = 1;
			}
			else {
				LOGGER.log(Level.WARNING, "Username already exists!");
			}
		} catch (HibernateException e) {
			transaction.rollback();
			LOGGER.log(Level.SEVERE, "Hibernate Exception", e);
			return status;
		}finally {
			session.close();
		}
		
		LOGGER.exiting(this.getClass().getName(), "verifyUniqueUsername");
		return status;
	}
		
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
				LOGGER.log(Level.SEVERE, "Session customer get failed", e);
				return -3;
			}
			
			switch (status) {
			case 1: // Tel Number
				if(telNumber.length() > TEL_NUMBER_LENGTH) {
					return -2;
				}else {
					customer.setTelNumber(telNumber);
					break;
				}	
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
	
	private boolean checkPassword(String password) {
		LOGGER.entering(this.getClass().getName(), "checkPassword");	
		Session session = factory.openSession();
		Transaction transaction = null;
		
		Account account;
		
		try {
			transaction=session.beginTransaction();
			
			try {
				account = session.get(Account.class, accountID);	
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Session account get failed", e);
				return false;
			}
			
			if(!account.getPassword().equals(passwordHashing(password))) {
				return false;
			}
			
			transaction.commit();
		} catch (HibernateException e) {
			LOGGER.log(Level.SEVERE, "Hibernate Exception", e);
			transaction.rollback();
			return false;
		}finally {
			session.close();
		}
		
		LOGGER.exiting(Controller.class.getName(), "checkPassword");
		return true;
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
	
	private boolean verifyAnswers(String answer, String answerDB) {
		LOGGER.entering(this.getClass().getName(), "verifyStrings");
		
		try {
			if(answer.equals(answerDB)) {
				LOGGER.log(Level.INFO, "Strings are " + answer + " " + answerDB);
				return true;
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Non identical answers", e);
			return false;
		}
		
		LOGGER.exiting(Controller.class.getName(), "verifyStrings");
		return false;
	}
	
	private boolean verifyTelNumber(TextField telNumber) {
		try {
			@SuppressWarnings("unused")
			long number = Long.parseLong(telNumber.getText().substring(1, 12));
		} catch (Exception e) {
			return false;
		}
	
		return true;
	}
	
	private boolean verifyRegistrationData(DatePicker date, TextField telNumber) {
		LocalDate localDate = date.getValue();
		
		if(telNumber.getText().length() > 13 || localDate.isAfter(LocalDate.of(MIN_DATE_OF_BIRTH, 1, 1))) {
			return false;
		}
		
		return true;
	}
	
	private boolean verifyPasswords(PasswordField password, PasswordField confirm) {
		if(!password.getText().equals(confirm.getText())) {
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
	
}
