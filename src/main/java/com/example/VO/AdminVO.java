package com.example.VO;

import java.io.Serializable;




public class AdminVO implements Serializable {
	private static final long serialVersionUID = 1L;


	private int adminId;


	private boolean isActive;


	private String adminEmailId;


	private boolean isSuperAdmin;


	private int projectId;

	public AdminVO() {
	}

	public int getAdminId() {
		return this.adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public boolean getActiveStatus() {
		return this.isActive;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.isActive = activeStatus;
	}

	public String getAdminEmailId() {
		return this.adminEmailId;
	}

	public void setAdminEmailId(String adminEmailId) {
		this.adminEmailId = adminEmailId;
	}

	public boolean getIsSuperAdmin() {
		return this.isSuperAdmin;
	}

	public void setIsSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public int getTProject() {
		return this.projectId;
	}

	public void setTProject(int TProject) {
		this.projectId = TProject;
	}

}