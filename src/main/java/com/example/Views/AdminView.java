package com.example.Views;

import com.example.WhatsUpApp.WhatsUpUI;
import com.example.components.Menu;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class AdminView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public static String NAME="Adminview";
	Grid FeedbackDisp;

	WhatsUpUI ui;

	public AdminView(WhatsUpUI ui) {
		this.ui=ui;
		init();
	}

	private void init() {


		VerticalLayout Content=new VerticalLayout();
		HorizontalLayout mainLayout=new HorizontalLayout();
		Navigator nav1=new Navigator(ui,Content);
		Menu menu=new Menu(nav1);
		menu.addView( new DashBoardView() ,"", "DashBoard", FontAwesome.USER_PLUS);
		menu.addView( new AdminView(ui) ,"AdminView", "DashBoard", FontAwesome.USER_PLUS);

		menu.setSizeFull();

		mainLayout.addComponents(menu,Content);
		mainLayout.setExpandRatio(menu, 15);
		mainLayout.setExpandRatio(Content, 85);
		mainLayout.setSizeFull();

		Content.setSpacing(true);
		Content.setMargin(true);
		Content.setSizeUndefined();

		addComponent(mainLayout);

		//ui.setContent(mainLayout);

//		rootLayout.addComponents(mainLayout);
//		Content.setComponentAlignment(FeedbackDisp, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
