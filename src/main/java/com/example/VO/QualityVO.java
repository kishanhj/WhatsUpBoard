package com.example.VO;

import java.io.Serializable;



public class QualityVO implements Serializable {
	private static final long serialVersionUID = 1L;


	private int qualityId;


	private String qualityDescription;


	private String qualityName;


	public QualityVO() {
	}

	public int getQualityId() {
		return this.qualityId;
	}

	public void setQualityId(int qualityId) {
		this.qualityId = qualityId;
	}

	public String getQualityDescription() {
		return this.qualityDescription;
	}

	public void setQualityDescription(String qualityDescription) {
		this.qualityDescription = qualityDescription;
	}

	public String getQualityName() {
		return this.qualityName;
	}

	public void setQualityName(String qualityName) {
		this.qualityName = qualityName;
	}


}