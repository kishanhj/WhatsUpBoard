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
import com.example.constants.ValidationConstants;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

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
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;
	}

	/**
	 * Extracts the feedbackId from feedback table based on month values
	 *
	 * @param feedback_month
	 *            feedback month
	 *
	 * @return int : FeedbackId
	 */
	public static int getFeedbackId(String feedback_month,int ProjectId) {

		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACK_ID_MONTH_QUERY);
			stmt.setString(1, feedback_month);
			stmt.setInt(2, ProjectId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else
				return 0;
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;
	}

	/**
	 * Extracts all feedbackIds from feedback table based on projectId and month
	 * values
	 *
	 * @param feedback_month
	 *            feedback month
	 *
	 * @param projectId
	 *            project Id
	 *
	 * @return int : feedbackIds
	 */
	public static List<Integer> getAllFeedbackId(String feedback_month, int projectId) {
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
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
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
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;

	}

	/**
	 * Inserts all employee feedbacks to the t_feedback
	 *
	 * @param employee
	 *            EmployeeVO for each employee
	 *
	 * @param month
	 *            month value
	 *
	 * @return boolean : Indicates success or failure
	 */
	public static boolean addFeedbackEntry(EmployeeVO employee, String month) {

		try {
			con = ConnectionUtils.getConnection();
			int isSuccessful;
			PreparedStatement stmt = con.prepareStatement(QueryConstant.INSERT_FEEDBACK_QUERY);
			stmt.setString(1, employee.getEmployeeId());
			stmt.setString(2, employee.getEmployeeEmailId());
			stmt.setString(3, employee.getEmployeeName());
			stmt.setString(4, month);
			stmt.setInt(5, employee.getProjectId());
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
	 * extracts employeeName from t_feedback based on feedbackId
	 *
	 * @param feedbackId
	 *            feedback Id
	 *
	 * @return string : employeeName
	 */
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
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}

	/**
	 * returns list of months from t_feedback
	 *
	 * @return monthList
	 *
	 */

	public static List<String> getMonthList(int projectId) {
		Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_MONTH_LIST_QUERY);
			stmt.setInt(1, projectId);
			ResultSet rs = stmt.executeQuery();
			List<String> monthList = new ArrayList<String>();
			while (rs.next()) {
				monthList.add(rs.getString(1));
			}
			return monthList;
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}

	/**
	 * extracts ProjectName from t_feedback based on feedbackId
	 *
	 * @param feedbackId
	 *            feedback Id
	 *
	 * @return string : ProjectName
	 */
	public static String getProjectName(int feedbackId) {
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACK_PROJECT_ID_QUERY);
			stmt.setInt(1, feedbackId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return ProjectDAO.getProjectName(rs.getInt(1));
			} else
				return null;

		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}

	/**
	 * Gets feedbackId from name
	 * @param employeeName
	 * @param feedback_month
	 * @return
	 */
	public static int getFeedbackIdFromName(String employeeName, String feedback_month) {
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACK_ID_NAME_QUERY);
			stmt.setString(1, feedback_month);
			stmt.setString(2, employeeName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			} else
				return 0;
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;
	}
}