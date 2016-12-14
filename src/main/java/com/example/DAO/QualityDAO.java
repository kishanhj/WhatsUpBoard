package com.example.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.Helpers.ConnectionUtils;
import com.example.VO.QualityVO;
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
			//System.out.println("After Connection");
			//System.out.println("Connection Object In getQualities : "+con);
			Statement stmt = con.createStatement();
			//System.out.println("After stmt");
			ResultSet rs = stmt.executeQuery("select * from t_quality");
			//System.out.println("After rs gen");
			qualities = new ArrayList<QualityVO>();
			//System.out.println("before rs");
			while (rs.next()) {
				quality = new QualityVO();
				quality.setQualityId(rs.getInt(1));
				quality.setQualityName(rs.getString(2));
				quality.setQualityDescription(rs.getString(3));
				qualities.add(quality);
			}
			//System.out.println(qualities.size());
			return qualities;

		} catch (SQLException e) {
			Notification.show("Failed to close connection");
			Page.getCurrent().reload();
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;

	}
}
