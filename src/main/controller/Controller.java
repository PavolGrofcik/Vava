package main.controller;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import main.entities.Customer;

/**
 * Trieda Controller obsahuje hlavné metódy s prácou DB a perzistenciou JPA
 * Používa sa návrhový vzor singleton
 * @author grofc
 *
 */

public class Controller {
	
	private SessionFactory factory;
	private static Controller controller = new Controller();
	
	//Private konštruktor - Singleton
	private Controller() {
		this.factory = HibernateUtil.getSessionFactory();
	}

	
	//Metóda vráti inštanciu pre Controller
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
	     controller.factory.close();						//Ukonèenie session nad DB
	}

}
