package com.example.validators;

import com.example.DAO.FeedbackDAO;
import com.vaadin.data.Validator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

public class MonthValidator implements Validator {

	private static final long serialVersionUID = 1L;

	String errorMsg;
	TextField employeeId;
	ComboBox month;

	public MonthValidator(String errorMsg, ComboBox month) {
		this.errorMsg= errorMsg;
		this.month=month;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
        int feedbackId=FeedbackDAO.getFeedbackId((String)month.getValue());
        if(feedbackId != 0)
        	 throw new InvalidValueException(errorMsg);

	}

}
