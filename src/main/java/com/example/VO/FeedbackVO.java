package com.example.VO;

import java.io.Serializable;
import java.util.List;

public class FeedbackVO implements Serializable {
	private static final long serialVersionUID = 1L;


	private int feedbackId;


	private String employeeEmailId;


	private String employeeId;


	private String employeeName;


	private String feedbackMonth;


	private int projectId;


	private List<QualityFeedbackVO> TFeedbackQualities;

	public FeedbackVO() {
	}

	public int getFeedbackId() {
		return this.feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getEmployeeEmailId() {
		return this.employeeEmailId;
	}

	public void setEmployeeEmailId(String employeeEmailId) {
		this.employeeEmailId = employeeEmailId;
	}

	public String getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getFeedbackMonth() {
		return this.feedbackMonth;
	}

	public void setFeedbackMonth(String feedbackMonth) {
		this.feedbackMonth = feedbackMonth;
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int TProject) {
		this.projectId = TProject;
	}

	public List<QualityFeedbackVO> getTFeedbackQualities() {
		return this.TFeedbackQualities;
	}

	public void setTFeedbackQualities(List<QualityFeedbackVO> TFeedbackQualities) {
		this.TFeedbackQualities = TFeedbackQualities;
	}


}