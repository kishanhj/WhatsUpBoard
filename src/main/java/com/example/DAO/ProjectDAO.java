package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Helpers.ConnectionUtils;
import com.example.VO.ProjectVO;
import com.example.constants.Constant;

public class ProjectDAO {

	private static Connection con = null;

	/**
	 * Returns the list of all project
	 *
	 * @return List<ProjectVO> :
	 *             list of projectVO's
	 */
	public static List<ProjectVO> getAllProjectDetails() {
		ProjectVO project = new ProjectVO();
		ArrayList<ProjectVO> project_details;
		try {
			con = ConnectionUtils.getConnection();

			PreparedStatement stmt = con.prepareStatement(Constant.GET_PROJECT_QUERY);
			ResultSet rs = stmt.executeQuery();
			project_details = new ArrayList<ProjectVO>();
			while (rs.next()) {
				project = new ProjectVO();
				project.setProjectId(rs.getInt(1));
				project.setProjectName(rs.getString(2));
				project.setActiveStatus(rs.getBoolean(3));
				project_details.add(project);
			}
			return project_details;

		} catch (SQLException e) {

			System.out.println("exception");
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;

	}

	/**
	 * Returns the list of all project names
	 *
	 * @return List<String> :
	 *             list of project names
	 */
	public static List<String> getAllProjects() {
		ArrayList<String> project_names;
		try {
			con = ConnectionUtils.getConnection();

			PreparedStatement stmt = con.prepareStatement(Constant.GET_PROJECT_NAMES_QUERY);
			ResultSet rs = stmt.executeQuery();
			project_names = new ArrayList<String>();
			while (rs.next()) {
				project_names.add(rs.getString(1));
			}
			return project_names;

		} catch (SQLException e) {
			System.out.println("exception");
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;

	}

	/**
	 * Returns all the details of the project from its name
	 *
	 * @param projectName
	 *         Name of the project
	 *
	 * @return ProjectVO
	 */
	public static ProjectVO getProject(String projectName) {
		//Connection con = null;
		try {
			con = ConnectionUtils.getConnection();
			ProjectVO projectvo = new ProjectVO();
			PreparedStatement stmt = con.prepareStatement(Constant.GET_PROJECT_QUERY);
			stmt.setString(1, projectName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				projectvo.setProjectId(rs.getInt(1));
				projectvo.setProjectName(rs.getString(2));
				projectvo.setActiveStatus(rs.getBoolean(3));
			}
			return projectvo;

		} catch (SQLException e) {
			System.out.println("exception");
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}


}