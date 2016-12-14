package com.example.WhatsUpApp;

import javax.servlet.annotation.WebServlet;

import com.example.Views.FeedbackFormView;
import com.example.Views.LoginView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;


@Theme("mytheme")
public class WhatsUpUI extends UI {
	private static final long serialVersionUID = 1L;

	@Override
    protected void init(VaadinRequest vaadinRequest) {

		Navigator nav=new Navigator(this,this);
		nav.addView(LoginView.NAME,new LoginView(this));
		nav.addView(FeedbackFormView.NAME,new FeedbackFormView(this));

		nav.navigateTo(FeedbackFormView.NAME);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = WhatsUpUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
    }
}
