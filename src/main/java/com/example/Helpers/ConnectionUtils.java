package com.example.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

	// public static Connection connection;

	/**
	 * Generates a connection object
	 *
	 * @return connection
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			if (connection == null) {
				// System.out.println("In getConnection to load driver");
				Class.forName("com.mysql.jdbc.Driver");
				String url = "jdbc:mysql://localhost/Watsup_board";
				connection = DriverManager.getConnection(url, "root", "root");
				// System.out.println("Connection Object after loading :
				// "+connection);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			// System.out.println("Con failed");
			// Notification.show("Connection Failed...Please reload and Try
			// Again");
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
