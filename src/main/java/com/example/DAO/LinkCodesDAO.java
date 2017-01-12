package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.example.Helpers.ConnectionUtils;
import com.example.constants.QueryConstant;
import com.vaadin.server.Page;
import com.vaadin.ui.TextField;

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

			Page.getCurrent().reload();
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
			e.printStackTrace();
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
              e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}

	}
}