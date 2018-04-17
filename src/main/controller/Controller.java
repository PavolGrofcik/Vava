package main.controller;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import main.entities.Customer;

/**
 * Trieda Controller, ktorá obsahuje hlavné metódy s prácou DB a perzistenciou
 * @author grofc
 *
 */

public class Controller {
	
	private SessionFactory factory;
	
	public Controller() {
		this.factory = HibernateUtil.getSessionFactory();
	}

	public static void main(String[] args) {
		Controller controller = new Controller();
		
		Customer cust;
		
		 Session session = controller.factory.openSession();
	     Transaction tx = session.beginTransaction();
	     
	     TypedQuery<Customer> query = session.createQuery("SELECT new Customer(firstName, lastName) FROM Customer WHERE id = :param1",Customer.class);
	     query.setParameter("param1", 1);
	     cust = query.getResultList().get(0);
	     
	     tx.commit();
				
				
	     System.out.println("Customer name: " + cust.getFirstName());
		
	     session.close();
	     controller.factory.close();						//Ukonèenie session nad DB
	}

}
