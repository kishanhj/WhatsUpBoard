package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Helpers.ConnectionUtils;
import com.example.VO.EmployeeVO;
import com.example.VO.FeedbackVO;
import com.example.constants.QueryConstant;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

public class FeedbackDAO {

	public static Connection con = null;

	/**
	 * Adds an entry to t_feedback table from a feedback VO
	 *
	 * @param feedback
	 *            A feedback VO with employee data
	 *
	 * @return boolean : Indicates success or failure
	 */
	public static boolean addFeedback(FeedbackVO feedback) {

		try {
			con = ConnectionUtils.getConnection();
			int isSuccessful;
			PreparedStatement stmt = con.prepareStatement(QueryConstant.INSERT_FEEDBACK_QUERY);
			stmt.setString(1, feedback.getEmployeeId());
			stmt.setString(2, feedback.getEmployeeEmailId());
			stmt.setString(3, feedback.getEmployeeName());
			stmt.setString(4, feedback.getFeedbackMonth());
			stmt.setInt(5, feedback.getProjectId());
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

	public static List<Integer> getAllFeedbackId(String feedback_month) {
		 Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACK_ID_MONTH_QUERY);
			stmt.setString(1, feedback_month);
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
	 * Checks whether the employeeId is present in the feedbackTable
	 *
	 * @param employee_id
	 *            EmployeeId to be checked
	 *
	 * @return boolean : Indicates whether the Id is exists or not
	 */
	public static boolean isValidEmployeeId(String employee_id) {
		// Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.EMPLOYEEID_VALIDATOR_QUERY);
			stmt.setString(1, employee_id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			} else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return false;
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

	public static String getemployeeName(int feedbackId) {
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
}
