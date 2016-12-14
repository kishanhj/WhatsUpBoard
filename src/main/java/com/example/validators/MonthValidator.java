package com.example.validators;

import com.example.DAO.FeedbackDAO;
import com.example.DAO.QualityFeedbackDAO;
import com.vaadin.data.Validator;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

public class MonthValidator implements Validator {

	private static final long serialVersionUID = 1L;

	String errorMsg;
	TextField employeeId;
	ComboBox month;

	public MonthValidator(String errorMsg, TextField employeeId, ComboBox month) {
		this.errorMsg= errorMsg;
		this.employeeId=employeeId;
		this.month=month;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
        int feedbackId=FeedbackDAO.getFeedbackId(employeeId.getValue(),(String)month.getValue());
        System.out.println("in month validator:"+feedbackId);
        if(!QualityFeedbackDAO.exists(feedbackId))
          throw new InvalidValueException(errorMsg);
	}

}
