package com.example.WhatsUpApp;

import javax.servlet.annotation.WebServlet;

import com.example.Mailer.Encoding;
import com.example.Views.ErrorView;
import com.example.Views.FeedbackFormView;
import com.example.Views.LoginView;
import com.example.validators.UrlValidator;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@PreserveOnRefresh
@Theme("mytheme")
public class WhatsUpUI extends UI {
	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		VerticalLayout rootLayout = new VerticalLayout();
		rootLayout.setSizeFull();

		String empIdCode = vaadinRequest.getParameter("a");
		String monthCode = vaadinRequest.getParameter("b");
		if (UrlValidator.validate(empIdCode, monthCode)) {
			String employeeId = Encoding.getCodeValue(empIdCode);
			String month = Encoding.getCodeValue(monthCode);
			rootLayout.addComponent(new FeedbackFormView(this, employeeId, month));

		}else if(empIdCode.equals("admin")){
			rootLayout.addComponent(new LoginView(this));
		}
		else {
			rootLayout.addComponent(new ErrorView());
		}
		setContent(rootLayout);
	}

	@WebServlet(urlPatterns = "/*", name = "WhatsUpApp", asyncSupported = true)
	@VaadinServletConfiguration(ui = WhatsUpUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
	}
}
