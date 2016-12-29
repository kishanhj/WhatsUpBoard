package com.example.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.example.WhatsUpApp.WhatsUpUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

/**
 * Admin's view
 */
public class AdminView extends VerticalLayout implements View {

	/**
	 * Name of this view in the Navigator
	 */
	public static final String NAME = "AdminView";

	private static final long serialVersionUID = 1L;

	/**
	 * Navigator for navigations within AdminView
	 */
	Navigator navigator;

	/**
	 * Copy of Main Whatsup UI instance
	 */
	WhatsUpUI ui;

	/**
	 * Main Layout
	 */
	private VerticalLayout rootLayout = new VerticalLayout();

	/**
	 * Layout whose content changes on navigation
	 */
	private VerticalLayout navgationLayout = new VerticalLayout();

	/**
	 * SplitPanel containing Menu and Navigation Layout
	 */
	private HorizontalSplitPanel menuAndContainerPanel;

	/**
	 * Constructor with ui instance as parameter
	 */
	public AdminView(WhatsUpUI ui) {
		this.ui = ui;

		menuAndContainerPanel = new HorizontalSplitPanel();
		menuAndContainerPanel.setSizeFull();
		menuAndContainerPanel.setSplitPosition(20);

		rootLayout.setSizeFull();
		rootLayout.addComponent(menuAndContainerPanel);

		navigator = new Navigator(ui, navgationLayout);
		navigator.addView(StartSurveyView.NAME, new StartSurveyView(ui));
		navigator.addView(ViewFeedbackView.NAME, new ViewFeedbackView(ui));
		navigator.addView(GenreateReportView.NAME, new GenreateReportView(ui));
		navigator.addView(SuperAdminView.NAME, new SuperAdminView(ui));
		navigator.navigateTo(StartSurveyView.NAME);

		setSizeFull();
		addComponent(buildLayout());
		ui.setContent(this);

	}

	/**
	 * Intialize split panel and adds it to mainlayout
	 * @return VerticalLayout
	 */
	private VerticalLayout buildLayout() {

		MenuView menu = new MenuView(ui);

		menuAndContainerPanel.setFirstComponent(menu);
		menuAndContainerPanel.setSecondComponent(navgationLayout);
		menuAndContainerPanel.addStyleName("mainramhsplitpanel");
		menuAndContainerPanel.setLocked(true);

		rootLayout.addComponent(menuAndContainerPanel);

		return rootLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
