package com.api.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManagerOLD {

	// WAP to read the properties file from
	// src/test/resources/config/config.properties

	private static Properties prop = new Properties();

	private ConfigManagerOLD() {
		// private Constructor !!!
	}

	static {

		// Operation of loading the properties file in memory
		// static block it will be executed! Once during class loading time !!!

		File configFile = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "config" + File.separator + "config.properties");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(configFile);
			prop.load(fileReader);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getProperty(String key) {

		return prop.getProperty(key);
	}

}
