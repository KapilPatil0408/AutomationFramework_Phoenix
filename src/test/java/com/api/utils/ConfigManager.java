package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	// WAP to read the properties file from
	// src/test/resources/config/config.properties

	private static Properties prop = new Properties();
	private static String path="config/config.properties";
	private static String env;

	private ConfigManager() {
		// private Constructor !!!
	}

	static {

		// Operation of loading the properties file in memory
		// static block it will be executed! Once during class loading time !!!
		
		env = System.getProperty("env","qa");
		env = env.toLowerCase().trim();
		
		switch(env) {
		case "dev" -> path= "config/config.dev.properties";
		
		case "qa" -> path= "config/config.qa.properties";
		
		case "uat"-> path="config/config.uat.properties";
		
		default -> path="config/config.qa.properties";
		}
		
		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(path);

		if(input == null) {
			throw new RuntimeException("Cannot find file at the path "+ path);
		}
		
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getProperty(String key) {

		return prop.getProperty(key);
	}

}
