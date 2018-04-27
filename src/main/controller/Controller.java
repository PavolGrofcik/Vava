package main.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import main.entities.Customer;
import main.entities.Event;

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

	
	//Metóda vráti inštanciu pre Controller
	public static Controller getInstance() {
		return controller;
	}
	
	
	public static void main(String[] args) {
		Controller controller = getInstance();
		Customer cust;
		Event event;
		
		 Session session = controller.factory.openSession();
	     Transaction tx = session.beginTransaction();
	     
	     /*TypedQuery<Customer> query = session.createQuery("SELECT new Customer(id ,firstName, lastName, date, sex, telNumber, city, address)" +  ""
	     		+ "FROM Customer WHERE id = :param1", Customer.class);
	     query.setParameter("param1", 1);
	     */
	     event=session.get(Event.class, 5);
	     //cust = query.getResultList().get(0);
	     cust = session.get(Customer.class, 1);
	     
	     tx.commit();
				
		event.showEventInfo();
		cust.showCustomerInfo();
	     
	     session.close();
	     controller.factory.close();						//Ukonèenie session nad DB
	}

}
