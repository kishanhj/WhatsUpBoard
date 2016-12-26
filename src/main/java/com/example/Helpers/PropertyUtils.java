package com.example.Helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

	private static Properties configProperties = new Properties();
	private String filename = "config.properties";

	public Properties getConfigProperties() {
		if (configProperties.size() == 0)
			loadProperties();
		System.out.println(configProperties);
		return configProperties;
	}

	private void loadProperties() {
		InputStream input = getClass().getClassLoader().getResourceAsStream(filename);
		try {
			configProperties.load(input);
			System.out.println(configProperties);
		} catch (IOException e) {
			System.out.println("Input stream fail ");
			e.printStackTrace();
		}

	}

	public  String getproperty(String name) {
		System.out.println(configProperties);
		return configProperties.getProperty(name);

	}

}
