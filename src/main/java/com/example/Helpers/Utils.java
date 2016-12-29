package com.example.Helpers;

import com.example.constants.StringConstants;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.themes.ValoTheme;

public class Utils {

	public static void toggle(Button button, TextArea text) {

		if (button.getCaption().equalsIgnoreCase("satisfied")) {
			button.setCaption("Not Satisfied");
			button.setIcon(FontAwesome.FROWN_O);
			//button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			//button.addStyleName(ValoTheme.BUTTON_LARGE);
			button.setStyleName(ValoTheme.BUTTON_DANGER);
			text.addValidator(new StringLengthValidator("Please enter valid Reason", 10, 100, false));
			//text.setEnabled(true);
		} else {
			button.setCaption("Satisfied");
			button.setStyleName(ValoTheme.BUTTON_FRIENDLY);
			button.setIcon(FontAwesome.SMILE_O);
			text.removeAllValidators();
			//text.setEnabled(false);
		}

	}

	public static void imageToggler(Button button, TextArea text) {

		if (button.getCaption().equalsIgnoreCase("satisfied")) {
			button.setCaption("Not Satisfied");
			button.setIcon(new ThemeResource("sad.png"));
			text.addValidator(new StringLengthValidator("Please enter a valid Reason", 10, 100, false));
		} else {
			button.setCaption("Satisfied");
			button.setIcon(new ThemeResource("bad.png"));
			text.removeAllValidators();
		}

	}


	public static Boolean captionToBooleanConvertor(Button qualityBtn) {
		if(qualityBtn.getCaption().equalsIgnoreCase("Satisfied"))
			return true;
		else
			return false;
	}

	public static String booleanToStringConvertor(boolean condition) {
		if(condition)
			return "YES";
		else
			return "NO";
	}

	public static String encode(String password) {
		Integer passwordHash = password.hashCode();
		passwordHash *= Integer.parseInt(StringConstants.PASSWORD_ENCODING_STRING);
		passwordHash += Integer.parseInt(StringConstants.PASSWORD_ENCODING_STRING);
		return passwordHash.toString();

	}

}
