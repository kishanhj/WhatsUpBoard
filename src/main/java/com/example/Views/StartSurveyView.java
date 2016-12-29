package com.example.Views;

import java.net.URL;
import java.util.List;
import java.util.Properties;

import com.example.DAO.EmployeeDAO;
import com.example.DAO.FeedbackDAO;
import com.example.DAO.ProjectDAO;
import com.example.Helpers.PropertyUtils;
import com.example.Mailer.Encoding;
import com.example.Mailer.MailUtils;
import com.example.Mailer.SendMail;
import com.example.VO.AdminVO;
import com.example.VO.EmployeeVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.example.constants.ValidationConstants;
import com.example.validators.MonthValidator;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class StartSurveyView extends HorizontalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "StartSurveyView";
	private WhatsUpUI ui;
	private AdminVO user;

	public StartSurveyView(WhatsUpUI ui) {
		this.ui=ui;
		 user=(AdminVO) ui.getSession().getAttribute("user");
		addComponent(init());

	}

	private VerticalLayout init() {

		VerticalLayout content = new VerticalLayout();
		ComboBox feedbackMonth = new ComboBox("Month");
		Properties prop1 = new PropertyUtils().getConfigProperties();
		String year = prop1.getProperty("year");
		addItems(feedbackMonth,year);

		feedbackMonth.setNullSelectionAllowed(false);
		feedbackMonth.setRequired(true);
		feedbackMonth.addValidator(new MonthValidator(ValidationConstants.MONTH_VALIDATOR, feedbackMonth));
		feedbackMonth.setValidationVisible(true);
		content.addComponent(feedbackMonth);

		content.setSpacing(true);
		content.setMargin(true);
		content.setSizeUndefined();
		content.setComponentAlignment(feedbackMonth, Alignment.MIDDLE_LEFT);

		Table employeelListTable = new Table("Employee List");
		employeelListTable.setWidth("800px");
		employeelListTable.setPageLength(8);

		IndexedContainer container = new IndexedContainer();

		employeelListTable.addStyleName("SliderBar");
		employeelListTable.setCaption("Employee List");


		container.addContainerProperty("Employee Name", String.class, null);
		container.addContainerProperty("Employee Id", String.class, null);
		container.addContainerProperty("Project", String.class, null);
		container.addContainerProperty("Email Id", String.class, null);

		employeelListTable.setContainerDataSource(container);
		employeelListTable.setPageLength(Math.min(container.size(), 15));

		employeelListTable.setColumnExpandRatio("Employee Name", 40);
		employeelListTable.setColumnExpandRatio("Employee Id", 30);
		employeelListTable.setColumnExpandRatio("Project", 30);
		employeelListTable.setColumnExpandRatio("Email Id", 80);

		content.addComponent(employeelListTable);
		content.setComponentAlignment(employeelListTable, Alignment.MIDDLE_CENTER);

		List<EmployeeVO> employees = EmployeeDAO.getEmployeeDetails();
		int i = 1;
		String projectName;
		for (EmployeeVO employee : employees) {
			if(user.getTProject() == employee.getProjectId()){
			projectName = ProjectDAO.getProjectName(employee.getProjectId());
			employeelListTable.addItem(new Object[] { employee.getEmployeeName(), employee.getEmployeeId(), projectName,
					employee.getEmployeeEmailId() }, i++);
		}}
		content.addComponent(employeelListTable);
		content.setComponentAlignment(employeelListTable, Alignment.MIDDLE_CENTER);

		Button startSurvey = new Button("Start Survey");

		startSurvey.addClickListener(e -> {
			feedbackMonth.validate();
			for (EmployeeVO employee : employees) {
				AdminVO user=(AdminVO) ui.getSession().getAttribute("user");
				if(user.getTProject() == employee.getProjectId()){
				Encoding.generatecodes(employee.getEmployeeId(), (String) feedbackMonth.getValue());
				FeedbackDAO.addFeedbackEntry(employee, (String) feedbackMonth.getValue());
				URL url = MailUtils.getUrl(employee.getEmployeeId(), (String) feedbackMonth.getValue());
				String mailBody = "Please complete the survey by clicking the below link<br><br>" + "<a href="
						+ url.toString() + ">here</a>";
				SendMail mailer = new SendMail(employee.getEmployeeEmailId(), mailBody);
				mailer.sendMail();
			}}
		});

		content.addComponent(startSurvey);

		return content;
	}

	private void addItems(ComboBox feedbackMonth, String year) {
		feedbackMonth.setPageLength(12);
		feedbackMonth.addItem("January " + year);
		feedbackMonth.addItem("February " + year);
		feedbackMonth.addItem("March " + year);
		feedbackMonth.addItem("April " + year);
		feedbackMonth.addItem("May " + year);
		feedbackMonth.addItem("June " + year);
		feedbackMonth.addItem("July " + year);
		feedbackMonth.addItem("August " + year);
		feedbackMonth.addItem("September " + year);
		feedbackMonth.addItem("October " + year);
		feedbackMonth.addItem("November " + year);
		feedbackMonth.addItem("December " + year);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
