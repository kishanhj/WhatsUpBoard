package com.example.VO;

import java.io.Serializable;



public class QualityFeedbackVO implements Serializable {
	private static final long serialVersionUID = 1L;


	private int feedbackQualityid;


	private String comment;


	private boolean satisfyIndicator;


	private int feedbackId;


	private int qualityId;

	public QualityFeedbackVO() {
	}

	public QualityFeedbackVO(int feedbackId, boolean satisfyIndicator,String comment,int qualityId ) {
		this.feedbackId=feedbackId;
		this.satisfyIndicator=satisfyIndicator;
		this.comment=comment;
		this.qualityId=qualityId;
	}

	public int getFeedbackQualityid() {
		return this.feedbackQualityid;
	}

	public void setFeedbackQualityid(int feedbackQualityid) {
		this.feedbackQualityid = feedbackQualityid;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean getSatisfyIndicator() {
		return this.satisfyIndicator;
	}

	public void setSatisfyIndicator(boolean satisfyIndicator) {
		this.satisfyIndicator = satisfyIndicator;
	}

	public int getFeedbackId() {
		return this.feedbackId;
	}

	public void setFeedbackId(int TFeedback) {
		this.feedbackId = TFeedback;
	}

	public int getQualityId() {
		return this.qualityId;
	}

	public void setQualityId(int TQuality) {
		this.qualityId = TQuality;
	}

}