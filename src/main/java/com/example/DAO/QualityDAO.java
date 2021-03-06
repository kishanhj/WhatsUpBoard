package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.Helpers.ConnectionUtils;
import com.example.VO.QualityVO;
import com.example.constants.QueryConstant;
import com.example.constants.ValidationConstants;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class QualityDAO {

	private static Connection con = null;

	/**
	 * Returns the list of all qualities in detail
	 *
	 * @return List of QualityVo
	 */
	public static List<QualityVO> getAllQualities() {
		// Connection con = null;
		try {
			QualityVO quality = new QualityVO();
			ArrayList<QualityVO> qualities;
			con = ConnectionUtils.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(QueryConstant.GET_ALL_QUALITIES);
			qualities = new ArrayList<QualityVO>();
			while (rs.next()) {
				quality = new QualityVO();
				quality.setQualityId(rs.getInt(1));
				quality.setQualityName(rs.getString(2));
				quality.setQualityDescription(rs.getString(3));
				qualities.add(quality);
			}
			return qualities;

		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;

	}

	/**
	 * extracts QualityName from t_quality based on qualityId
	 *
	 * @param qualityId
	 *
	 * @return string : QualityName
	 *
	 */
	public static String getQualityName(int qualityId) {
		try {

			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_QUALITY_NAME);
			stmt.setInt(1, qualityId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(1);
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}

	/**
	 * extracts qualityID from t_quality based on qualityName
	 *
	 * @param qualityName
	 *
	 * @return int : qualityID
	 *
	 */

	public static int getQualityID(String qualityName) {
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_QUALITY_ID);
			stmt.setString(1, qualityName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return 0;
	}
}