package com.example.Views;


import com.example.WhatsUpApp.WhatsUpUI;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class LoginView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static String NAME="Loginview";
	WhatsUpUI ui;

	public LoginView(WhatsUpUI ui) {
		this.ui=ui;
		//buildNotification();
		Component loginForm = buildLoginForm();

		Panel p=new Panel();
		p.setSizeUndefined();
		p.setContent(loginForm);

		setSizeFull();
		addComponent(p);
		setComponentAlignment(p, Alignment.MIDDLE_CENTER);

	}

	private Component buildLoginForm() {
		 final VerticalLayout loginPanel = new VerticalLayout();
	        loginPanel.setSizeUndefined();
	        loginPanel.setSpacing(true);
	        loginPanel.setMargin(true);
	        Responsive.makeResponsive(loginPanel);
	        loginPanel.addStyleName("login-panel");

	        Component Lables=buildLabels();
	        Component Feilds=buildFields();
	        loginPanel.addComponents(Lables,Feilds);

	        final Button forgotpw= new Button("Forgot password ?");
            forgotpw.setStyleName(ValoTheme.BUTTON_LINK);

	        loginPanel.addComponent(forgotpw);
	        loginPanel.setComponentAlignment(forgotpw, Alignment.TOP_RIGHT);

	        return loginPanel;
	}

	private Component buildFields() {
		 HorizontalLayout fields = new HorizontalLayout();
	        fields.setSpacing(true);
	        fields.addStyleName("fields");

	        final TextField username = new TextField("Username");
	        username.setIcon(FontAwesome.USER);
	        username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

	        final PasswordField password = new PasswordField("Password");
	        password.setIcon(FontAwesome.LOCK);
	        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

	        final Button signin = new Button("Sign In");
	        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
	        signin.setClickShortcut(KeyCode.ENTER);
	        signin.focus();

	        fields.addComponents(username, password, signin);
	        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

	        signin.addClickListener(new ClickListener() {

				private static final long serialVersionUID = 1L;

				@Override
	            public void buttonClick(final ClickEvent event) {
	            	if(username.getValue().equalsIgnoreCase("")&& password.getValue().equalsIgnoreCase("")){
                      Navigator nav=ui.getNavigator();
                      nav.navigateTo(FeedbackFormView.NAME);
	            }
	            	else {
	            			password.setValue("");
	            		Notification notification=new Notification("INVAILD USERNAME OR PASSWORD");
	            		notification.setPosition(Position.BOTTOM_CENTER);
	            		notification.show(Page.getCurrent());
	            	}
	            }});
	        return fields;
	}

	private Component buildLabels() {
		 HorizontalLayout labels = new HorizontalLayout();
	        labels.addStyleName("labels");
	        labels.setSizeFull();

	        Label welcome = new Label("Welcome");
	        welcome.setSizeFull();
	        welcome.addStyleName(ValoTheme.LABEL_H4);
	        welcome.addStyleName(ValoTheme.LABEL_COLORED);
	        labels.addComponent(welcome);

	        Label title = new Label("WhatsUp Board Feedback");
	        title.setSizeUndefined();
	        title.addStyleName(ValoTheme.LABEL_H3);
	        title.addStyleName(ValoTheme.LABEL_LIGHT);
	        labels.addComponent(title);
	        labels.setComponentAlignment(title, Alignment.TOP_RIGHT);
	        return labels;
	}

	@SuppressWarnings("unused")
	private void buildNotification() {
		Notification notification = new Notification("Welcome to WHATSUP Board Survey ");
		notification.setDescription("Please enter your Employee Id as Username and password1$ as password ");
       notification.setHtmlContentAllowed(true);
       notification.setStyleName("tray dark small closable login-help");
       notification.setPosition(Position.BOTTOM_CENTER);
       notification.setDelayMsec(20000);
       notification.show(Page.getCurrent());

	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
