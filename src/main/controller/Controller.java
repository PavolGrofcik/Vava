package main.controller;


import org.hibernate.SessionFactory;


/**
 * Trieda Controller obsahuje hlavn� met�dy pre pr�cu s DB a perzistenciou JPA
 * Pou��va sa n�vrhov� vzor Singleton
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
	


}
