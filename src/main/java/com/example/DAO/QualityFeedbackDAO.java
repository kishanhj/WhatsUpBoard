package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.Helpers.ConnectionUtils;
import com.example.VO.QualityFeedbackVO;
import com.example.constants.QueryConstant;

public class QualityFeedbackDAO {

	private static Connection con = null;

	/**
	 * Inserts all employee feedbacks to the t_quality_feedback
	 *
	 * @param qualityWiseFeedbacks
	 *         QualityFeedbackVO for each quality
	 *
	 * @return boolean : Indicates success or failure
	 *
	 */
	public static boolean addFeedbacks(List<QualityFeedbackVO> qualityWiseFeedbacks)  {

		try {
			con = ConnectionUtils.getConnection();
			int successfulUpdateCount = 0;
			for (QualityFeedbackVO quality : qualityWiseFeedbacks) {
				PreparedStatement stmt = con.prepareStatement(QueryConstant.INSERT_QUALITY_FEEDBACK_QUERY);
				stmt.setBoolean(1, quality.getSatisfyIndicator());
				stmt.setString(2, quality.getComment());
				stmt.setInt(3, quality.getFeedbackId());
				stmt.setInt(4, quality.getQualityId());
				successfulUpdateCount += stmt.executeUpdate();
			}
			if(successfulUpdateCount == 6)
			   return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con);
		}

		return false;
	}

	/**
	 * Checks whether feedback with this EmployeeId already e
	 * @param feedbackId
	 * @return
	 */
	public static boolean exists(int feedbackId) {
		try {
			//System.out.println("in quality feedback dao exists");
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.DOES_QUALITY_FEEDBACK_EXIST_QUERY);
			stmt.setInt(1, feedbackId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				return false;
			}else{
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionUtils.closeConnection(con);
		}
		return false;
	}
}