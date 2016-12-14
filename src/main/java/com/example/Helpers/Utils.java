package com.example.Helpers;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
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

	public static void imageToggle(Button button, TextArea text) {

		if (button.getCaption().equalsIgnoreCase("satisfied")) {
			button.setCaption("Not Satisfied");
			button.setIcon(new ThemeResource("em1.png"));
			button.setStyleName(ValoTheme.BUTTON_BORDERLESS);
			button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		//	button.setStyleName(ValoTheme.BUTTON_HUGE);
			text.addValidator(new StringLengthValidator("Please enter a valid Reason", 10, 100, false));
			text.setEnabled(true);
		} else {
			button.setCaption("Satisfied");
			button.setStyleName(ValoTheme.BUTTON_BORDERLESS);
			button.setStyleName(ValoTheme.BUTTON_ICON_ONLY);
		//	button.setStyleName(ValoTheme.BUTTON_HUGE);
			button.setIcon(new ThemeResource("em.jpg"));
			text.removeAllValidators();
			text.setEnabled(false);
		}

	}


	public static Boolean captionToBooleanConvertor(Button qualityBtn) {
		if(qualityBtn.getCaption().equalsIgnoreCase("Satisfied"))
			return true;
		else
			return false;
	}

}