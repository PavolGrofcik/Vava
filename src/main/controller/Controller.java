package main.controller;


import org.hibernate.SessionFactory;


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
	


}
