package com.example.VO;

import java.io.Serializable;

public class EmployeeVO implements Serializable{

	private static final long serialVersionUID = 1L;


	private String employeeEmailId;


	private String employeeId;


	private String employeeName;

	private int projectId;

	public String getEmployeeEmailId() {
		return employeeEmailId;
	}


	public void setEmployeeEmailId(String employeeEmailId) {
		this.employeeEmailId = employeeEmailId;
	}


	public String getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}


	public String getEmployeeName() {
		return employeeName;
	}


	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public int getProjectId() {
		return projectId;
	}


	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}


}
