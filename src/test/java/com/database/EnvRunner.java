package com.database;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvRunner {

	
	public static void main(String[] args) {
		// TODO Auto-g

		Dotenv dotenv =Dotenv.load();
		String data= dotenv.get("DB_URL");
		String data1= dotenv.get("DB_USER_NAME");
		String data2= dotenv.get("DB_PASSWORD");
		System.out.println(data);
		System.out.println(data1);
		System.out.println(data2);
		
		
	}

}
