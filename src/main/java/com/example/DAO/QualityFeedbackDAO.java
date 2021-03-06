package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Helpers.ConnectionUtils;
import com.example.VO.QualityFeedbackVO;
import com.example.constants.QueryConstant;
import com.example.constants.ValidationConstants;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

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
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}

		return false;
	}

	/**
	 * Checks whether feedback with this FeedbackId already exists
	 * @param feedbackId
	 * @return  Indicates true or false
	 *
	 */
	public static boolean exists(int feedbackId) {
		try {

			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.DOES_QUALITY_FEEDBACK_EXIST_QUERY);
			stmt.setInt(1, feedbackId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				return true;
			}else{
				return false;
			}

		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		}finally {
			ConnectionUtils.closeConnection(con);
		}
		return false;
	}
	/**
	 * extracts the feedbacks from t_feedback_quality based on feedback_month and projectId
	 * @param feedback_month
	 * @param projectId
	 * @return List of QualityFeedbackVO
	 *
	 */

	public static List<QualityFeedbackVO> getMonthWiseFeedbacks(ComboBox feedback_month,int projectId) {
		List<QualityFeedbackVO> feedbacks = new ArrayList<QualityFeedbackVO>();
		QualityFeedbackVO quality;
		String month=(String) feedback_month.getValue();
		List<Integer> feedbackIdsOfThisMonth=FeedbackDAO.getAllFeedbackId(month,projectId);
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACKS);
			for(int feedbackId:feedbackIdsOfThisMonth){
			stmt.setInt(1, feedbackId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				quality= new QualityFeedbackVO();
				quality.setSatisfyIndicator(rs.getBoolean(2));
				quality.setComment(rs.getString(3));
				quality.setFeedbackId(rs.getInt(4));
				quality.setQualityId(rs.getInt(5));
				feedbacks.add(quality);
			}}
			return feedbacks;
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		}finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;

	}

	/**
	 * extracts the feedbacks from t_feedback_quality based on feedback_month and projectId and qualityName
	 *
	 * @param feedback_month
	 * @param qualityName
	 * @param projectId
	 * @return List of QualityFeedbackVO
	 */

	public static List<QualityFeedbackVO> getQualiyWiseFeedbacks(ComboBox feedback_month, String qualityName,int projectId) {
		List<QualityFeedbackVO> feedbacks = new ArrayList<QualityFeedbackVO>();
		QualityFeedbackVO quality;
		String month=(String) feedback_month.getValue();
		List<Integer> feedbackIdsOfThisMonth=FeedbackDAO.getAllFeedbackId(month,projectId);
		int qualityId=QualityDAO.getQualityID(qualityName);
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_QUALITY_FEEDBACKS);
			for(int feedbackId:feedbackIdsOfThisMonth){
			stmt.setInt(1, feedbackId);
			stmt.setInt(2, qualityId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				quality= new QualityFeedbackVO();
				quality.setSatisfyIndicator(rs.getBoolean(2));
				quality.setComment(rs.getString(3));
				quality.setFeedbackId(rs.getInt(4));
				quality.setQualityId(rs.getInt(5));
				feedbacks.add(quality);
			}}
			return feedbacks;
		} catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		}finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}


	/**
	 * Gets the feedbacks based on feedbackID
	 * @param feedbackId
	 * @return
	 */
	public static List<QualityFeedbackVO> getFeedbacks(int feedbackId) {
		List<QualityFeedbackVO> feedbacks = new ArrayList<QualityFeedbackVO>();
		QualityFeedbackVO quality;
		try {
			con = ConnectionUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(QueryConstant.GET_FEEDBACKS);
			stmt.setInt(1, feedbackId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				quality = new QualityFeedbackVO();
				quality.setSatisfyIndicator(rs.getBoolean(2));
				quality.setComment(rs.getString(3));
				quality.setFeedbackId(rs.getInt(4));
				quality.setQualityId(rs.getInt(5));
				feedbacks.add(quality);
			}
			return feedbacks;
		}

		catch (SQLException e) {
			Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
		} finally {
			ConnectionUtils.closeConnection(con);
		}
		return null;
	}
}