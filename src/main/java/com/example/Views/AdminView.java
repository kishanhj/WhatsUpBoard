package com.example.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.DAO.EmployeeDAO;
import com.example.DAO.FeedbackDAO;
import com.example.DAO.ProjectDAO;
import com.example.DAO.QualityDAO;
import com.example.DAO.QualityFeedbackDAO;
import com.example.Helpers.PropertyUtils;
import com.example.Mailer.Encoding;
import com.example.Mailer.MailUtils;
import com.example.Mailer.SendMail;
import com.example.VO.EmployeeVO;
import com.example.VO.QualityFeedbackVO;
import com.example.VO.QualityVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.example.constants.ValidationConstants;
import com.example.report.ExcelReportGenerator;
import com.example.validators.MonthValidator;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class AdminView extends VerticalLayout implements View {

	public static final String NAME="AdminView";

	private static final long serialVersionUID = 1L;

	Table FeedbackDisp;

	Navigator nav;

	private VerticalLayout rootLayout = new VerticalLayout();
	private VerticalLayout navLayout = new VerticalLayout();


	private HorizontalSplitPanel hSplitPanel;


	public AdminView(WhatsUpUI ui) {

		hSplitPanel = new HorizontalSplitPanel();
		hSplitPanel.setSizeFull();
		hSplitPanel.setSplitPosition(20);
		rootLayout.addComponents(hSplitPanel);

		nav=new Navigator(ui,navLayout);
		nav.addView(StartSurveyView.NAME, new StartSurveyView());
		nav.addView(ViewFeedbackView.NAME, new ViewFeedbackView());
		nav.addView(GenreateReportView.NAME, new GenreateReportView());
		nav.navigateTo(StartSurveyView.NAME);


		addComponent(init());
		ui.setContent(this);

	}

	@SuppressWarnings("unchecked")
private VerticalLayout init() {

		MenuView menu = new MenuView();
		FeedbackDisp = new Table();
		menu.addComponent(FeedbackDisp);

         menu.setHeight("150");

		menu.setSizeUndefined();
		hSplitPanel.setFirstComponent(menu);
		hSplitPanel.setSecondComponent(navLayout);
		hSplitPanel.addStyleName("mainramhsplitpanel");


		rootLayout.addComponents(hSplitPanel);

		return rootLayout;
	}




	@Override
	public void enter(ViewChangeEvent event) {

	}

}
