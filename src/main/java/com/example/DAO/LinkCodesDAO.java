package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.example.Helpers.ConnectionUtils;
import com.example.constants.QueryConstant;
import com.example.constants.ValidationConstants;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Notification.Type;

public class LinkCodesDAO {

     /**
      * Add code to t_link_codes based on key and value
      * @param key
      * @param value
      * @return   Indicates success or failure
      */
	public static boolean addCode(String key,String value) {
		Connection con = null;
		try {
			 con = ConnectionUtils.getConnection();
			int isSuccessful;
			PreparedStatement stmt = con.prepareStatement(QueryConstant.INSERT_CODE_QUERY);
			stmt.setString(1, key);
			stmt.setString(2, value);
			isSuccessful = stmt.executeUpdate();
			if (isSuccessful == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}

		return false;
	}
/**
 *  Retrieves codes from t_link_codes
 * @return  codeList
 */
	public static HashMap<String, String> getCodes() {

		Connection con = null;
	    HashMap<String, String> codeList = new HashMap<>();
		try {
			 con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_CODE_QUERY);
            ResultSet rs= stmt.executeQuery();
            while(rs.next()){
            	codeList.put(rs.getString(1), rs.getString(2));
            }
          return codeList;
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}

		return null;
	}

	/**
	 *  delete a code based on employeeIdTextField
	 * @param employeeIdTextField
	 *
	 */
	public static void deleteCode(TextField employeeIdTextField) {
		Connection con = null;
		try {
			 con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.DELETE_CODE_QUERY);
			stmt.setString(1, employeeIdTextField.getValue());
             stmt.executeUpdate();
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}

	}

	/**
	 *  delete a code based on month
	 * @param employeeIdTextField
	 *
	 */
	public static void deleteCode(ComboBox month) {
		Connection con = null;
		try {
			 con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.DELETE_CODE_QUERY);
			stmt.setString(1, (String)month.getValue());
             stmt.executeUpdate();
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}

	}

	/**
	 *  delete a code based on employeeId
	 * @param employeeIdTextField
	 *
	 */
	public static void deleteCode(String employeeId) {
		Connection con = null;
		try {
			 con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.DELETE_CODE_QUERY);
			stmt.setString(1, employeeId);
             stmt.executeUpdate();
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}


	}
}