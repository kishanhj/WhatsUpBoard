package com.example.validators;

import com.example.constants.ValidationConstants;
import com.vaadin.data.Validator;
import com.vaadin.ui.PasswordField;

public class PasswordValidator implements Validator {

	private static final long serialVersionUID = 1L;

	private PasswordField password;
	String errorMsg;

	public PasswordValidator(PasswordField password, String errorMsg) {
		this.password = password;
		this.errorMsg = errorMsg;

	}

	@Override
	public void validate(Object value) throws InvalidValueException {
			if (password.getValue().equals("") || password.getValue() == null) {
				errorMsg = ValidationConstants.PASSWORD_VALIDATOR;
				throw new InvalidValueException(errorMsg);
	}

}
}
