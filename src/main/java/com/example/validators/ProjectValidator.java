package com.example.validators;

import com.example.DAO.FeedbackDAO;
import com.example.DAO.ProjectDAO;
import com.example.VO.ProjectVO;
import com.example.constants.ValidationConstants;
import com.vaadin.data.Validator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

public class ProjectValidator implements Validator {


	private static final long serialVersionUID = 1L;

	String errorMsg;
	
	String emptyStringMsg=ValidationConstants.EMPTY_STRING_MSG;
	
	TextField employeeId;
	
	ComboBox project;

	public ProjectValidator(String errorMsg,TextField employeeId,ComboBox project) {
	   this.errorMsg=errorMsg;
	   this.employeeId=employeeId;
	   this.project= project;
	}

	private boolean validityChecker(){
		String projectName=(String) project.getValue();
		ProjectVO projectvo = ProjectDAO.getProject(projectName);
		int projectId=FeedbackDAO.getFeedbackProject(employeeId.getValue());
		if(projectvo.getProjectId() == projectId)
			return true;
		else
			return false;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (!validityChecker()) {
			project.setValidationVisible(true);
			throw new InvalidValueException(errorMsg);
		}

	}

}
