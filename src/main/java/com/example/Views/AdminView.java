package com.example.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.example.WhatsUpApp.WhatsUpUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class AdminView extends VerticalLayout implements View {

	public static final String NAME="AdminView";

	private static final long serialVersionUID = 1L;

	Table FeedbackDisp;

	Navigator nav;

	WhatsUpUI ui;
	private VerticalLayout rootLayout = new VerticalLayout();
	private VerticalLayout navLayout = new VerticalLayout();


	private HorizontalSplitPanel hSplitPanel;


	public AdminView(WhatsUpUI ui) {
		this.ui=ui;
		hSplitPanel = new HorizontalSplitPanel();
		hSplitPanel.setSizeFull();
		hSplitPanel.setSplitPosition(20);
		rootLayout.addComponents(hSplitPanel);

		nav=new Navigator(ui,navLayout);
		nav.addView(StartSurveyView.NAME, new StartSurveyView(ui));
		nav.addView(ViewFeedbackView.NAME, new ViewFeedbackView(ui));
		nav.addView(GenreateReportView.NAME, new GenreateReportView(ui));
		nav.addView(SuperAdminView.NAME, new SuperAdminView(ui));
		nav.navigateTo(StartSurveyView.NAME);


		addComponent(init());
		ui.setContent(this);

	}

private VerticalLayout init() {

		MenuView menu = new MenuView(ui);
		FeedbackDisp = new Table();
		menu.addComponent(FeedbackDisp);

         menu.setHeight("150");

		menu.setSizeUndefined();
		hSplitPanel.setFirstComponent(menu);
		hSplitPanel.setSecondComponent(navLayout);
		hSplitPanel.addStyleName("mainramhsplitpanel");
		hSplitPanel.setLocked(true);


		rootLayout.addComponents(hSplitPanel);

		return rootLayout;
	}




	@Override
	public void enter(ViewChangeEvent event) {

	}

}
