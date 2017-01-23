package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Helpers.ConnectionUtils;
import com.example.VO.AdminVO;
import com.example.VO.EmployeeVO;
import com.example.constants.IntegerConstants;
import com.example.constants.QueryConstant;
import com.example.constants.ValidationConstants;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Notification.Type;

public class AdminDAO {

	/**
	 * Returns admin details based on username
	 *
	 * @param username
	 *            name of the user
	 *
	 * @return AdminVO
	 */
	public static AdminVO getAdmin(TextField username) {
		Connection con = null;
		try {
			AdminVO adminDetails = new AdminVO();
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_ADMIN_DETAILS_QUERY);
			stmt.setString(1, username.getValue());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				adminDetails.setActiveStatus(rs.getBoolean(1));
				adminDetails.setAdminId(rs.getString(2));
				adminDetails.setAdminEmailId(rs.getString(4));
				adminDetails.setIsSuperAdmin(rs.getBoolean(5));
				adminDetails.setTProject(rs.getInt(6));
				return adminDetails;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}

	/**
	 * add new admin to t_admin table
	 *
	 * @param admin
	 *            AdminVO
	 * @return Indicates success or failure
	 */

	public static int addAdmin(AdminVO admin) {
		Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.ADD_ADMIN_QUERY);
			EmployeeVO adminDetails = EmployeeDAO.getEmployee(admin.getAdminId());
			stmt.setString(1, admin.getAdminId());
			stmt.setString(2, admin.getPassword());
			stmt.setString(3, adminDetails.getEmployeeEmailId());
			stmt.setBoolean(4, admin.getIsSuperAdmin());
			stmt.setInt(5, adminDetails.getProjectId());
			return stmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;
	}

	/**
	 * checks whether this user is a valid admin, based on username and password
	 *
	 * @param username
	 * @param password
	 * @return Indicates success or failure
	 *
	 */
	public static boolean adminAuthentication(TextField username, String password) {
		Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.ADMIN_AUTHENTICATION_QUERY);
			stmt.setString(1, username.getValue());
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {

			Page.getCurrent().reload();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return false;
	}

	/**
	 * Returns all admin details
	 *
	 * @return AdminVO
	 */

	public static List<AdminVO> getAllAdminDetails() {
		Connection con = null;
		try {
			AdminVO adminDetails;
			List<AdminVO> admins = new ArrayList<>();
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_ALL_ADMIN_DETAILS_QUERY);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				adminDetails = new AdminVO();
				adminDetails.setActiveStatus(rs.getBoolean(1));
				adminDetails.setAdminId(rs.getString(2));
				adminDetails.setAdminEmailId(rs.getString(4));
				adminDetails.setIsSuperAdmin(rs.getBoolean(5));
				adminDetails.setTProject(rs.getInt(6));
				admins.add(adminDetails);
			}
			return admins;

		} catch (SQLException e) {

		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}

	/**
	 * Checks whether this emailId is already exists in t_admin
	 *
	 * @param emailId
	 * @return Indicates true or false
	 */
	public static boolean exist(TextField emailId) {
		Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.EMAIL_EXIST_QUERY);
			stmt.setString(1, emailId.getValue());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {

			Page.getCurrent().reload();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return false;
	}

	/**
	 * set new password using emailId and passwordHash
	 *
	 * @param emailId
	 *            email Id
	 * @param passwordHash
	 *            password Hashcode
	 *
	 * @return Status of update
	 */

	public static int setpassword(TextField emailId, String passwordHash) {
		Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.PASSWORD_RESET_QUERY);
			stmt.setString(1, passwordHash);
			stmt.setString(2, emailId.getValue());
			return stmt.executeUpdate();

		} catch (SQLException e) {
			Page.getCurrent().reload();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;

	}

	/**
	 * change the existing password using oldPasswordHash, newPasswordHash and
	 * adminId
	 *
	 * @param oldPasswordHash
	 *            Hash of old password
	 * @param newPasswordHash
	 *            Hash of new password
	 * @param adminId
	 *            admin Id
	 * @return Indicates success or failure
	 */

	public static boolean changePassword(String oldPasswordHash, String newPasswordHash, String adminId) {
		Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.PASSWORD_CHANGE_QUERY);
			stmt.setString(1, newPasswordHash);
			stmt.setString(2, oldPasswordHash);
			stmt.setString(3, adminId);
			if (IntegerConstants.ZERO != stmt.executeUpdate())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return false;

	}

	public static void deleteAdmin(String adminId) {
		Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.DELETE_ADMIN_QUERY);
			stmt.setString(1, adminId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
	}

	}

