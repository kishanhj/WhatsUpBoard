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
	    * Returns the list of all qualities in detail
	    *
	    * @return
	    *       List of QualityVo
	    */
		public static List<EmployeeVO> getEmployeeDetails()  {

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
				Notification.show("Failed to close connection");
				Page.getCurrent().reload();
			} finally {
				ConnectionUtils.closeConnection(con);
			}
			return null;

		}

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
			}
          return empDetails;

		} catch (SQLException e) {
			Notification.show("Failed to close connection");
			Page.getCurrent().reload();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}
	}


