package main.controller;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import main.entities.Customer;

/**
 * Trieda Controller obsahuje hlavn� met�dy s pr�cou DB a perzistenciou JPA
 * Pou��va sa n�vrhov� vzor singleton
 * @author grofc
 *
 */

public class Controller {
	
	private SessionFactory factory;
	private static Controller controller = new Controller();
	
	//Private kon�truktor - Singleton
	private Controller() {
		this.factory = HibernateUtil.getSessionFactory();
	}

	
	//Met�da vr�ti in�tanciu pre Controller
	public static Controller getInstance() {
		return controller;
	}
	
	
	public static void main(String[] args) {
		Controller controller = getInstance();
		Customer cust;
		
		 Session session = controller.factory.openSession();
	     Transaction tx = session.beginTransaction();
	     
	     TypedQuery<Customer> query = session.createQuery("SELECT new Customer(id ,firstName, lastName, date, sex, telNumber, city, address)" +  ""
	     		+ "FROM Customer WHERE id = :param1", Customer.class);
	     query.setParameter("param1", 1);
	     cust = query.getResultList().get(0);
	     
	     tx.commit();
				
				
	     cust.showCustomerInfo();							//How to solve date?????
		
	     session.close();
	     controller.factory.close();						//Ukon�enie session nad DB
	}

}
