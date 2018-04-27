package main.controller;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Trieda zodpovedn� za vytvorenie Session pre Hibernate Session DB
 * @author grofc
 *
 */

public class HibernateUtil {
	
	private static SessionFactory factory;
	
	//Nabindovanie session
	static {
		try {
			factory = new Configuration().configure("resources/hibernate.cfg.xml").buildSessionFactory();
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}
	
	
	//Met�da vr�ti Session factory pre Controllera
	public static SessionFactory getSessionFactory() {
		return factory;
	}

	
	//Met�da skon�� session s DB
	public static void closeSession() {
		factory.close();
	}
}
