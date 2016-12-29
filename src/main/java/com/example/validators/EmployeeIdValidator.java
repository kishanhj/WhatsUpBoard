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

	public EmployeeIdValidator(String errorMsg, TextField employeeId) {
		this.errorMsg = errorMsg;
		this.employeeId = employeeId;
	}

	private boolean validityChecker() {
		String empid = employeeId.getValue();
		if (empid.matches(".*[A-Za-z].*") || empid.length() < minLength || empid.length() > maxLength)
			return false;
		if (EmployeeDAO.getEmployee(empid) == null)
			return false;
		return true;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if(employeeId.getValue().equals(""))
			throw new InvalidValueException(emptyStringMsg);
		if (!validityChecker()) {
			employeeId.setValidationVisible(true);
			throw new InvalidValueException(errorMsg);
		}

	}

}
