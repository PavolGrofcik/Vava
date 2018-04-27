package main.controller;

<<<<<<< HEAD

import org.hibernate.SessionFactory;

=======
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.entities.Account;
import main.entities.Customer;
import main.entities.Event;
>>>>>>> 537c4ad418b65e817a6836fba8bf440e37c73b34

/**
 * Trieda Controller obsahuje hlavné metódy pre prácu s DB a perzistenciou JPA
 * Používa sa návrhový vzor Singleton
 * @author grofc
 *
 */

public class Controller {
	
	private static SessionFactory factory;
	private static Controller controller = new Controller();
	
	
	private Controller() {
		this.factory = HibernateUtil.getSessionFactory();
	}

<<<<<<< HEAD
	
	
=======
>>>>>>> 537c4ad418b65e817a6836fba8bf440e37c73b34
	public static Controller getInstance() {
		return controller;
	}
	
<<<<<<< HEAD

	public void shutDown() {
		factory.close();
	}
	


=======
	public void shutDown() {
		factory.close();
	}
	
	public int loginCustomer(TextField username, PasswordField password) throws NoSuchAlgorithmException {
		
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
	
		if(!checkInputs(username.getText(), password.getText())) {
			return -1;	// Missing values
		}
		
		String usernme = username.getText();
		String passwd;
		Account account;
		
		try {
			
			TypedQuery<Account> query = session.createQuery("SELECT new Account(customerId, controlQuestionId, " +
					"cash, username,password,answer) FROM Account WHERE username = :arg");
			query.setParameter("arg", usernme);
			
			if(query.getResultList().isEmpty()) {
				return - 2;		// Username does not exist
			}
			
			
			account = query.getResultList().get(0);
			
			String testString= password.getText();
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] messageDigest = md.digest(testString.getBytes());
	        BigInteger number = new BigInteger(1, messageDigest);
	        String hashtext = number.toString(16);
	        
			System.out.println(hashtext);
			
			if(!hashtext.equals(account.getPassword())) {
				return -2;		// Invalid password
			}
			
			transaction.commit();
			
		} catch (HibernateException e) {
			e.printStackTrace();
			transaction.rollback();
			return -3;		// Error
			
		}finally {
			session.close();
		}
		
		return 1;
	}
	
	
	private boolean checkInputs(String username, String password) {
		if(username.length() <=0 || password.length() <= 0) {
			return false;
		}
		
		return true;
	}
>>>>>>> 537c4ad418b65e817a6836fba8bf440e37c73b34
}
