package com.example.Views;


import com.example.DAO.AdminDAO;
import com.example.Helpers.Utils;
import com.example.Mailer.Encoding;
import com.example.Mailer.ForgotPassword;
import com.example.VO.AdminVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.label.ContentMode;
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
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

public class LoginView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static String NAME="Loginview";
	WhatsUpUI ui;

	/**
	 * Constructor
	 * @param ui
	 */
	public LoginView(WhatsUpUI ui) {
		this.ui=ui;
		Component loginForm = buildLoginForm();
		setStyleName("background6");
		//Component image1 = buildImage();
		//addComponent(image1);

		Panel mainPanel=new Panel();
		mainPanel.setSizeUndefined();
		mainPanel.setContent(loginForm);

		setSizeFull();
		addComponent(mainPanel);
		setComponentAlignment(mainPanel, Alignment.MIDDLE_CENTER);

	}

//	private Component buildImage() {
//
//		HorizontalLayout header1 = new HorizontalLayout();
//		// header1.setSizeUndefined();
//		header1.setWidth("100%");
//
//		header1.setStyleName("backgroundimage");
//		Resource res = new ThemeResource("brillio1.png");
//		Image image = new Image(null, res);
//		header1.addComponent(image);
//		Resource res2 = new ThemeResource("header2.png");
//		Image image3 = new Image(null, res2);
//		header1.addComponents(image, image3);
//		header1.setExpandRatio(image3, 2.0f);
//		header1.setComponentAlignment(image3, Alignment.MIDDLE_CENTER);
//		return header1;
//
//	}

	/**
	 * Builds the layout
	 * @return Component
	 */
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

            forgotpw.addClickListener(e -> {
            	Window window = new Window("Forgot Password");
            	window.setContent(buildForgotPWLayout(window));
            	window.center();
            	window.setResizable(false);
            	ui.addWindow(window);
            });

	        loginPanel.addComponent(forgotpw);
	        loginPanel.setComponentAlignment(forgotpw, Alignment.TOP_RIGHT);

	        return loginPanel;
	}

	/**
	 * builds forgot password layout
	 * @param window
	 * @return Component
	 */
	private Component buildForgotPWLayout(Window window) {
		VerticalLayout addProject = new VerticalLayout();
		addProject.setSpacing(true);
		addProject.setMargin(true);

		TextField emailId =new TextField("Email ID");

		Button ok_button = new Button();
		ok_button.setCaption("RESET");
		ok_button.addClickListener(e -> {
			if(AdminDAO.exist(emailId)){
			String newPassword = Encoding.randomCodeGenerator(6);
			String passwordHash=Utils.encode(newPassword);
			 AdminDAO.setpassword(emailId,passwordHash)	;
             ForgotPassword.sendMail(emailId,newPassword);
			window.close();
			Notification.show("Password has been mailed");
			}
			else
			Notification.show("FAILED", "This EmailId is not registered", Type.ERROR_MESSAGE);
		});

		Button cancel = new Button();
		cancel.setCaption("Cancel");
		cancel.addClickListener(e -> {
			window.close();
		});

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		buttons.addComponents(ok_button,cancel);
		buttons.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);
		buttons.setComponentAlignment(ok_button, Alignment.BOTTOM_LEFT);

       addProject.addComponents(emailId,buttons);

		return addProject;
	}

	/**
	 * Builds the fields
	 * @return Component
	 */
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
					String passwordHash = Utils.encode(password.getValue());
	            	if(AdminDAO.adminAuthentication(username, passwordHash)){
	            	  AdminVO admin = AdminDAO.getAdmin(username);
	            	  ui.getSession().setAttribute("user", admin);
                      ui.setContent(new AdminView(ui));
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

	/**
	 * Builds the lables
	 * @return Component
	 */
	private Component buildLabels() {
		 HorizontalLayout labels = new HorizontalLayout();
	        labels.addStyleName("labels");
	        labels.setSizeFull();

	        Label welcome = new Label("Brillio");
	        welcome.setSizeFull();
	        welcome.addStyleName(ValoTheme.LABEL_H2);
	        welcome.addStyleName(ValoTheme.LABEL_COLORED);
	        labels.addComponent(welcome);

	        Label title = new Label("<b>WhatsUp Board Feedback</b>");
	        title.setContentMode(ContentMode.HTML);
	        title.setSizeUndefined();
	        title.addStyleName(ValoTheme.LABEL_H3);
	        title.addStyleName(ValoTheme.LABEL_BOLD);
	        title.addStyleName(ValoTheme.LABEL_LIGHT);
	        labels.addComponent(title);
	        labels.setComponentAlignment(title, Alignment.TOP_RIGHT);
	        return labels;
	}


	@Override
	public void enter(ViewChangeEvent event) {

	}

}
