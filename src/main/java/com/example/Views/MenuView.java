package com.example.Views;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


public final class MenuView extends VerticalLayout {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static final String ID = "dashboard-menu";
    public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
    public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    private MenuItem settingsItem;



    public MenuView() {
        setPrimaryStyleName("valo-menu");
        setId(ID);
       // setSizeUndefined();
        setSizeFull();



        addComponent(buildContent());
        this.setSizeFull();
        this.setHeight("100%");
    }

    private Component buildContent() {
        final VerticalLayout menuContent = new VerticalLayout();
        menuContent.addStyleName("sidebar");
       menuContent.addStyleName(ValoTheme.MENU_PART);
//        menuContent.addStyleName("no-vertical-drag-hints");
//        menuContent.addStyleName("no-horizontal-drag-hints");
       // menuContent.setWidth("70px");
      //  menuContent.setHeight("100%");
     //   menuContent.setStyleName(ValoTheme.LAYOUT_CARD);
    //    menuContent.setSizeFull();
       menuContent.setSpacing(true);
        menuContent.setMargin(true);

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponents(buildUserLink());
        menuContent.addComponents(buildUserLink1());
        menuContent.addComponents(buildUserLink2());

//        menuContent.addComponent(buildUserMenu());
//        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());



        return menuContent;
    }

	private Component buildUserLink2() {

Button generateReport = new Button();

		generateReport.setCaption("Generate Report");
		VerticalLayout layy2 = new VerticalLayout();
		layy2.addComponent(generateReport);
		generateReport.setSizeFull();
		layy2.setComponentAlignment(generateReport, Alignment.MIDDLE_CENTER);
		generateReport.addClickListener(e -> {
			Navigator nav = getUI().getNavigator();
            nav.navigateTo(GenreateReportView.NAME);
		});
		return generateReport;

	}

	private Component buildUserLink1() {

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

	private Component buildUserLink() {
		Button startSurvey = new Button();

		startSurvey.setCaption("Start Survey");
		//button1.setSizeUndefined();
		startSurvey.setSizeFull();
	    //button1.setWidth("30px");
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
        Label logo = new Label("WhatupBoard <strong>Feedback</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }


    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        settingsItem = settings.addItem("",
                new ThemeResource("user_image.png"), null);
        settingsItem.addItem("Sign Out", new Command() {
            /**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void menuSelected(final MenuItem selectedItem) {

            }



        });

        return settings;
    }


    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            /**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void buttonClick(final ClickEvent event) {
                if (getStyleName()
                        .contains(STYLE_VISIBLE)) {
                    removeStyleName(STYLE_VISIBLE);
                } else {
                    addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.ANDROID);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
    	CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");


		return menuItemsLayout;
	}
}
