package main.controller;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Trieda zodpovedná za vytvorenie SessionFactory pri 
 * používaní ORM (Hibernate)
 * @author grofc
 *
 */

public class HibernateUtil {
	
	private static SessionFactory factory;
	
	//Bind config xml file
	static {
		try {
			factory = new Configuration().configure("resources/hibernate.cfg.xml").buildSessionFactory();
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}
	
	public static SessionFactory getSessionFactory() {
		return factory;
	}
	
	public static void closeSession() {
		factory.close();
	}
}
