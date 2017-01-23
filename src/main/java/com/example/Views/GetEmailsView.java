package com.example.Views;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class GetEmailsView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param month
	 * @param win
	 */
	public GetEmailsView() {
		addComponent(init());
	}

	/**
	 * builds the layout
	 * @return
	 */
	private Component init() {
		VerticalLayout mainLayout = new VerticalLayout();
		TextArea emails = new TextArea("please enter comma seperated emails");
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.addComponents(emails);
		return mainLayout;

	}
}
