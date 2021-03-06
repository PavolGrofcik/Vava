package main.controller;

import java.util.Random;

/**
 * Trieda zodpovedná za vygenerovanie bezpečnostného hesla,
 * pri obnove hesla používateľom
 * @author grofc
 *
 */

public class PasswdGenerator {
	
	private static final int BOUND = 58;
	private static final int LENGTH_OF_PASSWORD = 15;
	
	
	public static String generate() {
		Random random = new Random();
		
		String passwd = "";
		
		for(int i = 0;i<LENGTH_OF_PASSWORD;i++) {
			passwd += Character.toString((char)(random.nextInt(BOUND) + 65));
		}
		
		return passwd;
	}

}
