package main.controller;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.entities.Account;
import main.entities.Customer;


/**
 * Trieda Controller obsahuje hlavné metódy pre prácu s DB a perzistenciou JPA
 * Používa sa návrhový vzor Singleton
 * @author grofc
 *
 */

public class Controller {
	
	private SessionFactory factory;
	private static Controller controller = new Controller();
	
	
	private Controller() {
		this.factory = HibernateUtil.getSessionFactory();
	}

	public static Controller getInstance() {
		return controller;
	}
	
	public void shutDown() {
		factory.close();
	}
	

	public int loginCustomer(TextField username, PasswordField password) throws NoSuchAlgorithmException {
		
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
			
			if(!hashtext.equals(account.getPassword())) {
				return -2;		// Invalid password
			}
			
			transaction.commit();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			transaction.rollback();
			return -3;		// Fatal Error occured
			
		}finally {
			session.close();
		}
		
		return 1;
	}
	
	
	@SuppressWarnings("unchecked")
	public int sendNewPassword(TextField email, PasswordField answer, PasswordField password) {
		
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
			
			session.saveOrUpdate(account);
			
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return -4;
		}finally {
			session.close();
		}
		
		return 1;
	}
	
	
	private String passwordHashing(String password) {
		
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
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
	
	private boolean checkInput(String email, String answer, String password) {
		if(email.isEmpty() || answer.isEmpty() || password.isEmpty()) {
			return false;
		}
		
		return true;
	}

}
