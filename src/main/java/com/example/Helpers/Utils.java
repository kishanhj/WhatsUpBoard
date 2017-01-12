package com.example.Helpers;

import com.example.constants.StringConstants;
import com.example.constants.ValidationConstants;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextArea;

public class Utils {

	public static void imageToggler(Button button, TextArea text) {

		if (button.getCaption().equalsIgnoreCase(StringConstants.SATISFIED)) {
			button.setCaption(StringConstants.NOT_SATISFIED);
			button.setIcon(new ThemeResource(StringConstants.UNSATISFIED_IMAGE));
			text.addValidator(new StringLengthValidator(ValidationConstants.REASON_VALIDATOR_MSG, 10, 100, false));
		} else {
			button.setCaption(StringConstants.SATISFIED);
			button.setIcon(new ThemeResource(StringConstants.SATISFIED_IMAGE));
			text.removeAllValidators();
		}

	}


	public static Boolean captionToBooleanConvertor(Button qualityBtn) {
		if(qualityBtn.getCaption().equalsIgnoreCase(StringConstants.SATISFIED))
			return true;
		else
			return false;
	}

	public static String booleanToStringConvertor(boolean condition) {
		if(condition)
			return StringConstants.YES;
		else
			return StringConstants.NO;
	}

	public static String encode(String password) {
		Integer passwordHash = password.hashCode();
		passwordHash *= Integer.parseInt(StringConstants.PASSWORD_ENCODING_STRING);
		passwordHash += Integer.parseInt(StringConstants.PASSWORD_ENCODING_STRING);
		return passwordHash.toString();

	}

}
