package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.Helpers.ConnectionUtils;
import com.example.VO.EmployeeVO;
import com.example.constants.QueryConstant;
import com.example.constants.ValidationConstants;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class EmployeeDAO {
	private static Connection con = null;

	/**
	 * Returns the list of all employee details
	 *
	 * @return List of EmployeeVO
	 */
	public static List<EmployeeVO> getEmployeeDetails() {

		try {
			EmployeeVO empDetails = new EmployeeVO();
			ArrayList<EmployeeVO> EmpList;
			con = ConnectionUtils.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(QueryConstant.GET_ALL_EMPLOYEE_QUERY);

			EmpList = new ArrayList<EmployeeVO>();

			while (rs.next()) {
				empDetails = new EmployeeVO();
				empDetails.setEmployeeId(rs.getString(1));
				empDetails.setEmployeeEmailId(rs.getString(2));
				empDetails.setEmployeeName(rs.getString(3));
				empDetails.setProjectId(rs.getInt(4));
				empDetails.setIsactive(rs.getBoolean(5));
				EmpList.add(empDetails);
			}
			return EmpList;

		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;

	}

	/**
	 * Returns the list of all employee details of a project
	 *
	 * @return List of EmployeeVO
	 */
	public static List<EmployeeVO> getEmployeeDetails(int projectID) {

		try {
			EmployeeVO empDetails = new EmployeeVO();
			ArrayList<EmployeeVO> EmpList;
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_ALL_PROJECT_EMPLOYEE_QUERY);
			stmt.setInt(1, projectID);
			ResultSet rs = stmt.executeQuery();

			EmpList = new ArrayList<EmployeeVO>();

			while (rs.next()) {
				empDetails = new EmployeeVO();
				empDetails.setEmployeeId(rs.getString(1));
				empDetails.setEmployeeEmailId(rs.getString(2));
				empDetails.setEmployeeName(rs.getString(3));
				empDetails.setProjectId(rs.getInt(4));
				empDetails.setIsactive(rs.getBoolean(5));
				EmpList.add(empDetails);
			}
			return EmpList;

		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;

	}

	/**
	 * Returns all the details of the employee from its employeeId
	 *
	 * @param employeeId
	 *            Id of an Employee
	 *
	 * @return EmployeeVO
	 */

	public static EmployeeVO getEmployee(String employeeId) {

		try {
			EmployeeVO empDetails = new EmployeeVO();
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_EMPLOYEE_QUERY);
			stmt.setString(1, employeeId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				empDetails = new EmployeeVO();
				empDetails.setEmployeeId(rs.getString(1));
				empDetails.setEmployeeEmailId(rs.getString(2));
				empDetails.setEmployeeName(rs.getString(3));
				empDetails.setProjectId(rs.getInt(4));
				empDetails.setIsactive(rs.getBoolean(5));
				return empDetails;
			}

		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}

	/**
	 * Extracts the employeeName from employee table based on employeeId
	 *
	 * @param employeeId
	 *
	 * @return  employeeName
	 */
	public static String getEmployeeName(String employeeId) {

		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_EMPLOYEE_QUERY);
			stmt.setString(1, employeeId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getString(3);
			}

		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}

	/**
	 * Returns number of employees
	 * @param tProject
	 * @return
	 */
	public static int getEmployeeCount(int tProject) {
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_EMPLOYEE_COUNT_QUERY);
			stmt.setInt(1, tProject);
		    ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;
	}

	/**
	 * Checks whether a employee exists
	 * @param Id
	 * @return
	 */
	public static boolean exists(String Id) {
		try{
		con = ConnectionUtils.getConnection();
		PreparedStatement stmt = con.prepareStatement(QueryConstant.EMPLOYEE_EXISTS_QUERY);
		stmt.setString(1, Id);
	    ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return true;
		}
	} catch (SQLException e) {
		Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
	} finally {
		ConnectionUtils.closeConnection(con);
	}
	return false;
	}

	/**
	 * Adds a new employee
	 * @param name
	 * @param Id
	 * @param email
	 * @param tProject
	 */
	public static void addEmployee(String name, String Id, String email, int tProject) {
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.INSERT_EMPLOYEE_QUERY);
			stmt.setString(1, Id);
			stmt.setString(2, email);
			stmt.setString(3, name);
			stmt.setInt(4, tProject);
		    stmt.executeUpdate();
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
	}

	/**
	 * Deletes a employee
	 * @param employeeId
	 * @return
	 */
	public static boolean delete(String employeeId) {
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.DELETE_EMPLOYEE_QUERY);
			stmt.setString(1, employeeId);
		    if(stmt.executeUpdate() == 0)
		    	return false;
		    else
		    	return true;

		} catch (SQLException e) {
			e.printStackTrace();
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
			return false;
		} finally {
			ConnectionUtils.closeConnection(con);
		}
	}

	}



