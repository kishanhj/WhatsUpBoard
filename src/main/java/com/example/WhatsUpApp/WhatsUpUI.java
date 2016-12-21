package com.example.WhatsUpApp;

import javax.servlet.annotation.WebServlet;

import com.example.Mailer.Encoding;
import com.example.Mailer.MailUtils;
import com.example.Views.FeedbackFormView;
import com.example.validators.UrlValidator;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;

@Theme("mytheme")
public class WhatsUpUI extends UI {
	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		// Navigator nav=new Navigator(this,this);
		// nav.addView(LoginView.NAME,new LoginView(this));
		// nav.addView(FeedbackFormView.NAME,new FeedbackFormView(this));
		//
		// nav.navigateTo(FeedbackFormView.NAME);

		String empIdCode = vaadinRequest.getParameter("a");
		String monthCode = vaadinRequest.getParameter("b");
		if (UrlValidator.validate(empIdCode, monthCode)){
		    String employeeId=Encoding.getCodeValue(empIdCode);
		    String month=Encoding.getCodeValue(monthCode);
			setContent(new FeedbackFormView(this,employeeId,month));
		}
		else
			setContent(new Link("CLick Here", new ExternalResource(MailUtils.getUrl("113666", "January 2017"))));

	}

	@WebServlet(urlPatterns = "/*", name = "WhatsUpApp", asyncSupported = true)
	@VaadinServletConfiguration(ui = WhatsUpUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
	}
}
