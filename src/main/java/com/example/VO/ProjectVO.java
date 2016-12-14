package com.example.VO;

import java.io.Serializable;
import java.util.List;


public class ProjectVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int projectId;

	private boolean isActive;

	private String projectName;

	private List<AdminVO> admins;

	public ProjectVO() {
	}

	public int getProjectId() {
		return this.projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public boolean getActiveStatus() {
		return this.isActive;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.isActive = activeStatus;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<AdminVO> getTAdmins() {
		return this.admins;
	}

	public void setTAdmins(List<AdminVO> TAdmins) {
		this.admins = TAdmins;
	}

}