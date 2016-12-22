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
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;


public class QualityDAO {

	private static Connection con = null;

   /**
    * Returns the list of all qualities in detail
    *
    * @return
    *       List of QualityVo
    */
	public static List<QualityVO> getAllQualities()  {
		//Connection con = null;
		try {
			QualityVO quality = new QualityVO();
			ArrayList<QualityVO> qualities;
			con = ConnectionUtils.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from t_quality");
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
			Notification.show("Failed to close connection");
			Page.getCurrent().reload();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;

	}

public static String getQualityName(int qualityId) {
	try {

		con = ConnectionUtils.getConnection();
		PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_QUALITY_NAME);
		stmt.setInt(1, qualityId);
		ResultSet rs = stmt.executeQuery();
        if(rs.next())
        	return rs.getString(1);
	} catch (SQLException e) {
		Notification.show("Failed to close connection");
		Page.getCurrent().reload();
	} finally {
		ConnectionUtils.closeConnection(con);
	}
	return null;
}
}
