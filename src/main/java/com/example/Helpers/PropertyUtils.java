package com.example.Helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.example.constants.StringConstants;
import com.example.constants.ValidationConstants;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class PropertyUtils {

	private static Properties configProperties = new Properties();
	private String filename = StringConstants.CONFIG_FILENAME;

	/**
	 * Returns the properties in file
	 * @return ConfigProperties
	 */
	public Properties getConfigProperties() {
		if (configProperties.size() == 0)
			loadProperties();
		return configProperties;
	}

	/**
	 * Loads the properties from the file
	 */
	private void loadProperties() {
		InputStream input = getClass().getClassLoader().getResourceAsStream(filename);
		try {
			configProperties.load(input);
		} catch (IOException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		}

	}

	/**
	 * Returns the property value for given name
	 * @param name Property name
	 * @return Value of the property
	 */
	public  String getproperty(String name) {
		if(configProperties.isEmpty())
			loadProperties();
		return configProperties.getProperty(name);
	}

}
