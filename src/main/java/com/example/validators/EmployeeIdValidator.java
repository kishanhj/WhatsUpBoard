package com.example.validators;

import com.example.DAO.EmployeeDAO;
import com.example.constants.ValidationConstants;
import com.vaadin.data.Validator;
import com.vaadin.ui.TextField;

public class EmployeeIdValidator implements Validator {

	private static final long serialVersionUID = 1L;

	String errorMsg;

	 String emptyStringMsg=ValidationConstants.EMPTY_STRING_MSG;

	 TextField employeeId;

	 int minLength = 4;

	 int maxLength = 6;

	 boolean isNew = false;

	/**
	 * Constructor
	 * @param errorMsg
	 * @param employeeId
	 */
	public EmployeeIdValidator(String errorMsg, TextField employeeId) {
		this.errorMsg = errorMsg;
		this.employeeId = employeeId;
	}

	public EmployeeIdValidator(String errorMsg, TextField employeeId,boolean isNew) {
		this.errorMsg = errorMsg;
		this.employeeId = employeeId;
		this.isNew = isNew;
	}

	/**
	 * checks the validity of employee ID
	 * @return
	 */
	private  boolean validityChecker(TextField employeeId) {
		String empid = employeeId.getValue();
		int length = empid.length();
		if (empid.matches(".*[A-Za-z].*") || length < minLength || length> maxLength)
			return false;
		if(!isNew){
		if (EmployeeDAO.getEmployee(empid) == null)
			return false;
		}
		return true;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if(employeeId.getValue().equals("") || employeeId.getValue()==null){
			throw new InvalidValueException(emptyStringMsg);
		}
		if (!validityChecker(employeeId)) {
			employeeId.setValidationVisible(true);
			throw new InvalidValueException(errorMsg);
		}

	}

}
