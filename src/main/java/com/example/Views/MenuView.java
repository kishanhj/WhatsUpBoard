package com.example.Views;

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
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
       // menuContent.setWidth("70px");
        menuContent.setHeight("100%");
        menuContent.setStyleName(ValoTheme.LAYOUT_CARD);
        menuContent.setSizeFull();
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

Button button3 = new Button();

		button3.setCaption("Generate Report");
		//button3.setSizeUndefined();
		VerticalLayout layy2 = new VerticalLayout();
		layy2.addComponent(button3);
		 //button3.setWidth("30px");
		button3.setSizeFull();
		layy2.setComponentAlignment(button3, Alignment.MIDDLE_CENTER);
		return button3;

	}

	private Component buildUserLink1() {

       Button button2 = new Button();

		button2.setCaption("View Feedback");
		button2.setSizeUndefined();
		VerticalLayout layy1 = new VerticalLayout();
		layy1.addComponent(button2);
		button2.setSizeFull();
		layy1.setComponentAlignment(button2, Alignment.MIDDLE_CENTER);
		return button2;
	}

	private Component buildUserLink() {
		Button button1 = new Button();

		button1.setCaption("Start   Survey");
		//button1.setSizeUndefined();
		button1.setSizeFull();
	    //button1.setWidth("30px");
		button1.setSizeFull();
		VerticalLayout layy = new VerticalLayout();
		layy.addComponent(button1);

		layy.setComponentAlignment(button1, Alignment.MIDDLE_CENTER);

//		button1.addClickListener(e -> {
//			Navigator nav = getUI().getNavigator();
//
//			nav.navigateTo ("init()");
//			//nav.navigateTo ("AdminView");
//
//			// rootLayout.removeAllComponents();
//			// rootLayout.addComponent(new FeedbackFormView(rootLayout));
//
//
//			});


			return button1;
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
//        settingsItem = settings.addItem("",
//                new ThemeResource("image_dp.jpg"), null);
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
