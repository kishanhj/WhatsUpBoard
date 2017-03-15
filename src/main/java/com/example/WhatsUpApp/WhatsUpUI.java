package com.example.WhatsUpApp;

import javax.servlet.annotation.WebServlet;

import com.example.Mailer.Encoding;
import com.example.Views.ErrorView;
import com.example.Views.FeedbackFormView;
import com.example.Views.LoginView;
import com.example.constants.StringConstants;
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
     try{
		String empIdCode = vaadinRequest.getParameter(StringConstants.A);
		String monthCode = vaadinRequest.getParameter(StringConstants.B);
		if (UrlValidator.validate(empIdCode, monthCode)) {
			String employeeId = Encoding.getCodeValue(empIdCode);
			String month_project = Encoding.getCodeValue(monthCode);
			String month = month_project.substring(0, month_project.indexOf("_"));
			rootLayout.addComponent(new FeedbackFormView(this, employeeId, month));

		}else if(empIdCode.equals(StringConstants.ADMIN)){
			rootLayout.addComponent(new LoginView(this));
		}
		else {
			rootLayout.addComponent(new ErrorView());
		}
		setContent(rootLayout);
     }catch (Exception e) {
    	 rootLayout.addComponent(new ErrorView());
    	 setContent(rootLayout);
	}
	}

	@WebServlet(urlPatterns = "/*", name = "WhatsUpApp", asyncSupported = true)
	@VaadinServletConfiguration(ui = WhatsUpUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
	}
}
