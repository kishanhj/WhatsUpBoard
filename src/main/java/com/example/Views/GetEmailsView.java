package com.example.Views;

import com.example.Mailer.SendReports;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class GetEmailsView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	ComboBox month;
	Window window;

	/**
	 * Constructor
	 * @param month
	 * @param win
	 */
	public GetEmailsView(ComboBox month, Window win) {
		this.month = month;
		this.window = win;
		addComponent(init());
	}

	/**
	 * builds the layout
	 * @return
	 */
	private Component init() {
		VerticalLayout mainLayout = new VerticalLayout();
		TextArea emails = new TextArea("please enter comma seperated emails");
		Button sendMails = new Button("Send Mails");
		String reportMonth = month.getValue().toString();
		sendMails.addClickListener(e -> {
			SendReports reportMailer = new SendReports(emails.getValue(), reportMonth);
			reportMailer.sendReports();
			window.close();
			Notification.show("Mails have been sent");
		});
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.addComponents(emails, sendMails);
		return mainLayout;

	}
}
