package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Helpers.ConnectionUtils;
import com.example.VO.EmployeeVO;
import com.example.constants.QueryConstant;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class FeedbackDAO {

	public static Connection con = null;


	/**
	 * Extracts the feedbackId from feedback table based on employeeId and month
	 * values
	 *
	 * @param employee_id
	 *            employeeId of the user
	 *
	 * @param feedback_month
	 *            feedback month
	 *
	 * @return int : FeedbackId
	 *
	 */
	public static int getFeedbackId(String employee_id, String feedback_month) {
		// Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACK_ID_QUERY);
			stmt.setString(1, employee_id);
			stmt.setString(2, feedback_month);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;
	}


	public static int getFeedbackId(String feedback_month) {
		// Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACK_ID_MONTH_QUERY);
			stmt.setString(1, feedback_month);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;
	}

	public static List<Integer> getAllFeedbackId(String feedback_month,int projectId) {
		 Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACK_ID_MONTH_AND_PROJECT_QUERY);
			stmt.setString(1, feedback_month);
			stmt.setInt(2, projectId);
			ResultSet rs = stmt.executeQuery();
			List<Integer> feedbackIds = new ArrayList<Integer>();
			while (rs.next()) {
				feedbackIds.add(rs.getInt(1));
			}
			return feedbackIds;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}



	/**
	 * Returns the projectId of the employee
	 *
	 * @param employeeId
	 *            EmployeeId of the employee
	 *
	 * @return int : projectId of the employee
	 */
	public static int getFeedbackProject(String employeeId) {
		// Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACK_PROJECT_QUERY);
			stmt.setString(1, employeeId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;

	}

	public static boolean addFeedbackEntry(EmployeeVO employee, String month) {

		try {
			con = ConnectionUtils.getConnection();
			int isSuccessful;
			PreparedStatement stmt = con.prepareStatement(QueryConstant.INSERT_FEEDBACK_QUERY);
			stmt.setString(1, employee.getEmployeeId());
			stmt.setString(2, employee.getEmployeeEmailId());
			stmt.setString(3, employee.getEmployeeName());
			stmt.setString(4,  month);
			stmt.setInt(5, employee.getProjectId());
			isSuccessful = stmt.executeUpdate();
			if (isSuccessful == 1)
				return true;
			else
				return false;

		} catch (SQLException e) {
			Notification.show("Failed to Update");
			Page.getCurrent().reload();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return false;
	}

	public static String getEmployeeName(int feedbackId) {
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_EMPLOYEE_NAME_QUERY);
			stmt.setInt(1, feedbackId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}

	public static List<String> getMonthList() {
		Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_MONTH_LIST_QUERY);
			ResultSet rs = stmt.executeQuery();
			List<String> monthList = new ArrayList<String>();
			while (rs.next()) {
				monthList.add(rs.getString(1));
			}
			return monthList;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}

	public static String getProjectName(int feedbackId) {
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACK_PROJECT_ID_QUERY);
			stmt.setInt(1, feedbackId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return ProjectDAO.getProjectName(rs.getInt(1)) ;
			} else
				return null;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}
}
