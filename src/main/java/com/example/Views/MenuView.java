package com.example.Views;

import com.example.DAO.AdminDAO;
import com.example.DAO.EmployeeDAO;
import com.example.Helpers.Utils;
import com.example.VO.AdminVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public final class MenuView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	public static final String ID = "dashboard-menu";
	public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
	public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
	private MenuItem settingsItem;
	WhatsUpUI ui;
	AdminVO user;

	/**
	 * Constructor
	 * @param ui
	 */
	public MenuView(WhatsUpUI ui) {
		this.ui = ui;
		user = (AdminVO) ui.getSession().getAttribute("user");
		setPrimaryStyleName("valo-menu");
		setId(ID);

		addStyleName("panelcolor");
		setSizeFull();
		addComponent(buildContent());

	}

	/**
	 * Builds the layout
	 * @return
	 */
	private VerticalLayout buildContent() {
		final VerticalLayout menuContent = new VerticalLayout();
		menuContent.addStyleName("sidebar");
		menuContent.addStyleName(ValoTheme.MENU_PART);
		menuContent.setSpacing(true);
		menuContent.setMargin(true);

		Component menu = buildUserMenu();

		menuContent.addComponents(buildTitle(), menu, buildStartFeedBackbutton(), buildViewFeedBackButton(), buildGenerateReportButton());
		if (user.getIsSuperAdmin()) {
			menuContent.addComponents(buildSuperAdminButton());
		}
		// menuContent.addComponent(buildToggleButton());
		// menuContent.addComponent(buildMenuItems());

		menuContent.setComponentAlignment(menu, Alignment.MIDDLE_CENTER);

		return menuContent;
	}

	/**
	 * Builds SuperAdmin Button
	 * @return
	 */
	private Component buildSuperAdminButton() {
		Button generateReport = new Button();

		generateReport.setCaption("Super Admin function");
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(generateReport);
		generateReport.setSizeFull();
		layout.setComponentAlignment(generateReport, Alignment.MIDDLE_CENTER);
		generateReport.addClickListener(e -> {
			Navigator nav = getUI().getNavigator();
			nav.navigateTo(SuperAdminView.NAME);
		});
		return generateReport;
	}

	/**
	 * Builds Generate report button
	 * @return
	 */
	private Component buildGenerateReportButton() {

		Button generateReport = new Button();

		generateReport.setCaption("Generate Report");
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(generateReport);
		generateReport.setSizeFull();
		layout.setComponentAlignment(generateReport, Alignment.MIDDLE_CENTER);
		generateReport.addClickListener(e -> {
			Navigator nav = getUI().getNavigator();
			nav.navigateTo(GenreateReportView.NAME);
		});
		return generateReport;

	}

	private Component buildViewFeedBackButton() {

		Button viewFeedback = new Button();

		viewFeedback.setCaption("View Feedback");
		viewFeedback.setSizeUndefined();
		VerticalLayout layy1 = new VerticalLayout();
		layy1.addComponent(viewFeedback);
		viewFeedback.setSizeFull();
		layy1.setComponentAlignment(viewFeedback, Alignment.MIDDLE_CENTER);
		viewFeedback.addClickListener(e -> {
			Navigator nav = getUI().getNavigator();
			nav.navigateTo(ViewFeedbackView.NAME);
		});
		return viewFeedback;
	}

	private Component buildStartFeedBackbutton() {
		Button startSurvey = new Button();

		startSurvey.setCaption("Start Survey");
		startSurvey.setSizeFull();
		startSurvey.setSizeFull();
		VerticalLayout layy = new VerticalLayout();
		layy.addComponent(startSurvey);

		layy.setComponentAlignment(startSurvey, Alignment.MIDDLE_CENTER);

		startSurvey.addClickListener(e -> {
			Navigator nav = getUI().getNavigator();
			nav.navigateTo(StartSurveyView.NAME);
		});

		return startSurvey;
	}

	private Component buildTitle() {
		Label logo = new Label("WhatupBoard <strong>Feedback</strong>", ContentMode.HTML);
		logo.setSizeUndefined();
		HorizontalLayout logoWrapper = new HorizontalLayout(logo);
		logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		logoWrapper.addStyleName("valo-menu-title");
		return logoWrapper;
	}

	/**
	 * Builds the menu part
	 * @return
	 */
	private Component buildUserMenu() {
		final MenuBar settings = new MenuBar();
		settings.addStyleName("user-menu");
		settings.setCaptionAsHtml(true);
		settings.setCaption(
				"<center><strong>Welcome " + EmployeeDAO.getEmployeeName(user.getAdminId()) + "</strong></center>");
		settingsItem = settings.addItem("", new ThemeResource("UserImage.png"), null);
		settingsItem.addItem("Sign Out", new Command() {
			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				WhatsUpUI ui = (WhatsUpUI) getUI();
				ui.getSession().setAttribute("user", null);
				ui.setContent(new LoginView(ui));
			}

		});
		settingsItem.addItem("Change Password", new Command() {

			private static final long serialVersionUID = 1L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				Window window = new Window("Change password");
				window.setContent(changePasswordLayout(window));
				window.center();
				ui.addWindow(window);

			}

		});

		return settings;
	}

	/**
	 * Builds change password layout
	 * @param window
	 * @return
	 */
	private Component changePasswordLayout(Window window) {
		VerticalLayout addProject = new VerticalLayout();
		addProject.setSpacing(true);
		addProject.setMargin(true);

		PasswordField oldPassword = new PasswordField("Old Password");
		PasswordField newPassword = new PasswordField("New Password");

		Button ok_button = new Button();
		ok_button.setCaption("Change");
		ok_button.addClickListener(e -> {
			String oldPasswordHash = Utils.encode(oldPassword.getValue());
			String newPasswordHash = Utils.encode(newPassword.getValue());
			if (AdminDAO.changePassword(oldPasswordHash, newPasswordHash, user.getAdminId())) {
				Notification.show("Succesfull Changed");
				window.close();
			} else {
				Notification.show("Incorrect Old Password");
				oldPassword.setValue("");
			}
		});

		Button cancel = new Button();
		cancel.setCaption("Cancel");
		cancel.addClickListener(e -> {
			window.close();
		});

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		buttons.addComponents(ok_button, cancel);
		buttons.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);
		buttons.setComponentAlignment(ok_button, Alignment.BOTTOM_LEFT);

		addProject.addComponents(oldPassword, newPassword, buttons);

		return addProject;

	}

}
