package com.example.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.example.constants.StringConstants;

public class ConnectionUtils {
	/**
	 * Generates a connection object
	 *
	 * @return connection
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			if (connection == null) {
				Class.forName(StringConstants.SQL_DRIVER);
				String url = StringConstants.SQL_URL;
				String dataBaseName = new PropertyUtils().getproperty("DatabaseName");
				url=url+dataBaseName;
				connection = DriverManager.getConnection(url, StringConstants.SQL_USERNAME, StringConstants.SQL_PASSWORD);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
