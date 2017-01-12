package com.example.validators;

import com.example.DAO.FeedbackDAO;
import com.vaadin.data.Validator;
import com.vaadin.ui.ComboBox;

/**
 * Validates the Month
 * @author kishan.j
 *
 */
public class MonthValidator implements Validator {

	private static final long serialVersionUID = 1L;

	private String errorMsg;
	private ComboBox month;
	private int projectId;

	/**
	 * Constructor
	 * @param errorMsg
	 * @param month
	 * @param projectId
	 */
	public MonthValidator(String errorMsg, ComboBox month, int projectId) {
		this.errorMsg= errorMsg;
		this.month=month;
		this.projectId=projectId;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
        int feedbackId=FeedbackDAO.getFeedbackId((String)month.getValue(),projectId);
        if(feedbackId != 0)
        	 throw new InvalidValueException(errorMsg);

	}

}
