package com.example.Views;

import java.util.List;
import java.util.Properties;

import com.example.DAO.EmployeeDAO;
import com.example.DAO.FeedbackDAO;
import com.example.DAO.QualityDAO;
import com.example.DAO.QualityFeedbackDAO;
import com.example.Helpers.PropertyUtils;
import com.example.Mailer.SendReports;
import com.example.VO.AdminVO;
import com.example.VO.EmployeeVO;
import com.example.VO.QualityFeedbackVO;
import com.example.VO.QualityVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.example.constants.IntegerConstants;
import com.example.constants.StringConstants;
import com.example.report.ExcelReportGenerator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class GenreateReportView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	/**
	 * Name of this view in the Navigator
	 */
	public static final String NAME = "GenreateReportView";

	/**
	 * Holds the instance of current UI
	 */
	WhatsUpUI ui;

	/**
	 * Holds information of currently Loggedin User
	 */
	private AdminVO user;

	/**
	 * Indicates whether the survey is completed
	 */
	boolean isSurveyCompleted= false;

	/**
	 * Constructor with ui instance as parameter
	 *
	 * @param ui
	 *            WhatsUpUI
	 */
	public GenreateReportView(WhatsUpUI ui) {
		this.ui = ui;
		user = (AdminVO) ui.getSession().getAttribute(StringConstants.USER);
		addComponent(buildLayout());
	}

	/**
	 * Builds the UI Components
	 * @return
	 */
	private VerticalLayout buildLayout() {
		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);


		ComboBox feedbackMonth = new ComboBox();
        feedbackMonth.setCaption(StringConstants.MONTH);

        List<String> months = FeedbackDAO.getMonthList(user.getTProject());
		for (String month : months)
			feedbackMonth.addItem(month);

		content.addComponent(feedbackMonth);
		content.setComponentAlignment(feedbackMonth, Alignment.MIDDLE_LEFT);

		feedbackMonth.addValueChangeListener(e ->{
			List<EmployeeVO> employees = EmployeeDAO.getEmployeeDetails(user.getTProject());
			float leftcount=0;
			for (EmployeeVO employee : employees) {
					int feedbackId = FeedbackDAO.getFeedbackId(employee.getEmployeeId(), (String) feedbackMonth.getValue());
					if (!QualityFeedbackDAO.exists(feedbackId)){
						leftcount++;
					}
			}
			if(leftcount/employees.size() == IntegerConstants.F_ONE)
				isSurveyCompleted = true;
		});

		Button generateReport = new Button(StringConstants.GENERATE_REPORT);

		TextArea emails = new TextArea("please enter comma seperated emails");

		generateReport.addClickListener(e -> {
			if(feedbackMonth.getValue() == null){
				Notification.show("ERROR", "Please select a month",Type.ERROR_MESSAGE);
				return;
			}
			if(emails.getValue() == null||emails.getValue().equals("")){
				Notification.show("ERROR", "Please enter a valid EmailID",Type.ERROR_MESSAGE);
				return ;
			}
			if (!isSurveyCompleted) {
				Window window = new Window();
				window.setContent(buildConformationWindow(window, feedbackMonth,emails));
				window.center();
				window.setResizable(false);
				ui.addWindow(window);
			} else
				triggerReportGeneration(emails,feedbackMonth);

		});



		content.addComponents(emails,generateReport);
		return content;

	}


	/**
	 * Builds UI for popup conformation window
	 * @param window
	 * @param feedbackMonth
	 * @param emails
	 * @return VerticalLayout
	 */
	private Component buildConformationWindow(Window window, ComboBox feedbackMonth, TextArea emails) {
		VerticalLayout confirmationLayout = new VerticalLayout();
		HorizontalLayout buttons = new HorizontalLayout();

		Label msg = new Label(StringConstants.CONFORMATION_MSG);
		msg.setContentMode(ContentMode.HTML);
		Label confirm = new Label(StringConstants.CONFORMATION);

		Button yes = new Button(StringConstants.YES);
		Button no = new Button(StringConstants.NO);

		yes.addClickListener(e -> {
			window.close();
			triggerReportGeneration(emails,feedbackMonth);
		});

		no.addClickListener(e -> window.close());

		buttons.addComponents(yes, no);
		buttons.setSizeFull();
		buttons.setComponentAlignment(yes, Alignment.TOP_RIGHT);
		buttons.setComponentAlignment(no, Alignment.TOP_LEFT);

		confirmationLayout.addComponents(msg, confirm, buttons);

		confirmationLayout.setMargin(true);
		confirmationLayout.setSpacing(true);
		return confirmationLayout;
	}

	/**
	 * Triggers report generation
	 * @param emails
	 * @param feedbackMonth
	 */
	private void triggerReportGeneration(TextArea emails, ComboBox feedbackMonth) {
		generateReport(feedbackMonth);
		SendReports reportMailer = new SendReports(emails.getValue(), (String)feedbackMonth.getValue());
		if(reportMailer.sendReports())
		Notification.show("Mails have been sent");

	}

	/**
	 * Generates report
	 * @param feedbackMonth
	 */
	private void generateReport(ComboBox feedbackMonth) {
		ExcelReportGenerator excelReport = new ExcelReportGenerator(feedbackMonth);
		List<QualityVO> qualities = QualityDAO.getAllQualities();
		Properties prop = new PropertyUtils().getConfigProperties();
		float satisfactoryPercentage = Float.parseFloat((String) prop.get(StringConstants.PROP_SASISFIED)) / 100;
		for (QualityVO quality : qualities) {
			float positiveResponses = 0;
			float negetiveResponses = 0;
			List<QualityFeedbackVO> feedbacks = QualityFeedbackDAO.getQualiyWiseFeedbacks(feedbackMonth,
					quality.getQualityName(), user.getTProject());
			for (QualityFeedbackVO qualityfeedback : feedbacks) {

				if (qualityfeedback.getSatisfyIndicator())
					positiveResponses++;
				else
					negetiveResponses++;
			}
			float percentage = (positiveResponses / (positiveResponses + negetiveResponses));
			if (percentage < satisfactoryPercentage) {
				excelReport.addRow(quality.getQualityName(), StringConstants.NOT_SATISFACTORY, percentage);
			} else {
				excelReport.addRow(quality.getQualityName(), StringConstants.SATISFACTORY, percentage);
			}
		}
		generateAreaWiseSheets(excelReport, qualities, feedbackMonth);
		excelReport.generateReport();

	}

	/**
	 * Generate area wise sheets in report
	 * @param excelReport
	 * @param qualities
	 * @param feedbackMonth
	 */
	private void generateAreaWiseSheets(ExcelReportGenerator excelReport, List<QualityVO> qualities,
			ComboBox feedbackMonth) {
		String employeeName;
		String feedbackType;
		String projectName;
		for (QualityVO quality : qualities) {
			excelReport.GenerateAreaSheet(quality.getQualityName());
			List<QualityFeedbackVO> feedbacks = QualityFeedbackDAO.getQualiyWiseFeedbacks(feedbackMonth,
					quality.getQualityName(), user.getTProject());
			for (QualityFeedbackVO qualityfeedback : feedbacks) {
				employeeName = FeedbackDAO.getEmployeeName(qualityfeedback.getFeedbackId());
				projectName = FeedbackDAO.getProjectName(qualityfeedback.getFeedbackId());
				feedbackType = qualityfeedback.getSatisfyIndicator() ? StringConstants.SATISFIED  : StringConstants.NOT_SATISFIED;
				excelReport.addRow(employeeName, projectName, feedbackType, qualityfeedback.getComment());
			}
		}
	}


	@Override
	public void enter(ViewChangeEvent event) {

	}

}
