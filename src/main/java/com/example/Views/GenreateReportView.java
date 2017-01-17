package com.example.Views;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.DAO.EmployeeDAO;
import com.example.DAO.FeedbackDAO;
import com.example.DAO.QualityDAO;
import com.example.DAO.QualityFeedbackDAO;
import com.example.Helpers.PropertyUtils;
import com.example.VO.AdminVO;
import com.example.VO.QualityFeedbackVO;
import com.example.VO.QualityVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.example.constants.IntegerConstants;
import com.example.constants.StringConstants;
import com.example.report.ExcelReportGenerator;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
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

		IndexedContainer feedbackContainer = buildContainer();

		Table viewFeedbackTable = BuildTable(feedbackContainer);

		ProgressBar completedFeedbacks = new ProgressBar(viewFeedbackTable.size());
		completedFeedbacks.setWidth(StringConstants.NINE_HUNDRED_PX);

		completedFeedbacks.addContextClickListener(e -> {
			float count = completedFeedbacks.getValue() * 100;
			int employeesLeft = (int) (EmployeeDAO.getEmployeeCount(user.getTProject())
					* (1 - completedFeedbacks.getValue()));
			Notification.show(count + StringConstants.PROGRESS_BAR_MSG + employeesLeft);
		});

		feedbackMonth.addValueChangeListener(e -> {
			viewFeedbackTable.removeAllItems();
			showFeedbacks(viewFeedbackTable, feedbackMonth);
			viewFeedbackTable.setPageLength(Math.min(feedbackContainer.size(), 10));
			int employeeCount = EmployeeDAO.getEmployeeCount(user.getTProject());
			completedFeedbacks.setValue((float) feedbackContainer.size() / (employeeCount * 6));
		});


		viewFeedbackTable.addStyleName(StringConstants.STYLE_SLIDER_BAR);
		viewFeedbackTable.setSelectable(true);

		Button generateReport = new Button(StringConstants.GENERATE_REPORT);

		generateReport.addClickListener(e -> {
			if (completedFeedbacks.getValue() < IntegerConstants.ONE) {
				Window window = new Window();
				window.setContent(buildConformationWindow(window, feedbackMonth));
				window.center();
				ui.addWindow(window);
			} else
				triggerReportGeneration(feedbackMonth);

		});

		content.addComponents(completedFeedbacks,viewFeedbackTable,generateReport);
		return content;

	}

	/**
	 * Builds the container
	 * @return IndexedContainer
	 */
	private IndexedContainer buildContainer() {
		IndexedContainer feedbackContainer = new IndexedContainer();
		feedbackContainer.addContainerProperty(StringConstants.EMPLOYEE_NAME, String.class, null);
		feedbackContainer.addContainerProperty(StringConstants.QUALITY_NAME, String.class, null);
		feedbackContainer.addContainerProperty(StringConstants.IS_SATISFIED, String.class, null);
		feedbackContainer.addContainerProperty(StringConstants.COMMENTS, String.class, null);
		return feedbackContainer;
	}

	/**
	 * Builds the table
	 * @param feedbackContainer  A IndexedContainer
	 * @return Table
	 */
	private Table BuildTable(IndexedContainer feedbackContainer) {
		Table viewFeedbackTable = new Table();
		viewFeedbackTable.setWidth(StringConstants.NINE_HUNDRED_PX);
		viewFeedbackTable.setPageLength(15);

		viewFeedbackTable.setColumnExpandRatio(StringConstants.EMPLOYEE_NAME, 40);
		viewFeedbackTable.setColumnExpandRatio(StringConstants.QUALITY_NAME, 40);
		viewFeedbackTable.setColumnExpandRatio(StringConstants.IS_SATISFIED, 50);
		viewFeedbackTable.setColumnExpandRatio(StringConstants.COMMENTS, 80);

		viewFeedbackTable.setContainerDataSource(feedbackContainer);

		viewFeedbackTable.setPageLength(Math.min(feedbackContainer.size(), 10));
		return viewFeedbackTable;
	}

	/**
	 * Builds UI for popup conformation window
	 * @param window
	 * @param feedbackMonth
	 * @return VerticalLayout
	 */
	private VerticalLayout buildConformationWindow(Window window, ComboBox feedbackMonth) {
		VerticalLayout confirmationLayout = new VerticalLayout();
		HorizontalLayout buttons = new HorizontalLayout();

		Label msg = new Label(StringConstants.CONFORMATION_MSG);
		Label confirm = new Label(StringConstants.CONFORMATION);

		Button yes = new Button(StringConstants.YES);
		Button no = new Button(StringConstants.NO);

		yes.addClickListener(e -> {
			window.close();
			triggerReportGeneration(feedbackMonth);
		});

		no.addClickListener(e -> window.close());

		buttons.addComponents(yes, no);
		buttons.setSizeFull();
		buttons.setComponentAlignment(yes, Alignment.TOP_CENTER);
		buttons.setComponentAlignment(no, Alignment.TOP_CENTER);

		confirmationLayout.addComponents(msg, confirm, buttons);

		confirmationLayout.setMargin(true);
		confirmationLayout.setSpacing(true);
		return confirmationLayout;
	}

	/**
	 * Triggers report generation
	 * @param feedbackMonth
	 */
	private void triggerReportGeneration(ComboBox feedbackMonth) {
		generateReport(feedbackMonth);
		Window win = new Window(StringConstants.ENTER_EMAIL);
		win.setContent(new GetEmailsView(feedbackMonth, win));
		win.center();
		getUI().addWindow(win);

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

	/**
	 * Displays feedbacks in the table
	 * @param view_feedback
	 * @param feedback_month
	 */
	private void showFeedbacks(Table view_feedback, ComboBox feedback_month) {
		List<QualityFeedbackVO> feedbacks = QualityFeedbackDAO.getMonthWiseFeedbacks(feedback_month,
				user.getTProject());
		String employeeName;
		String qualityName;
		String feedbackType;
		int i = 1;
		for (QualityFeedbackVO feedback : feedbacks) {
			employeeName = FeedbackDAO.getEmployeeName(feedback.getFeedbackId());
			qualityName = QualityDAO.getQualityName(feedback.getQualityId());
			feedbackType = feedback.getSatisfyIndicator() ? StringConstants.SATISFIED  : StringConstants.NOT_SATISFIED;
			view_feedback.addItem(new Object[] { employeeName, qualityName, feedbackType, feedback.getComment() }, i++);
			ArrayList<String> varialble = new ArrayList<String>();
			if (varialble.size() != 0) {
				if (!varialble.contains(employeeName)) {
					varialble.add(employeeName);
				}
			}

		}
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
