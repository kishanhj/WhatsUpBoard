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

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

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
				EmpList.add(empDetails);
			}
			return EmpList;

		} catch (SQLException e) {
			Page.getCurrent().reload();
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
				return empDetails;
			}

		} catch (SQLException e) {
			Page.getCurrent().reload();
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
			e.printStackTrace();
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
			Notification.show("Failed to close connection");
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;
	}
	}


