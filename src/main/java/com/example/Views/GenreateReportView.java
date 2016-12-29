package com.example.Views;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.example.DAO.FeedbackDAO;
import com.example.DAO.QualityDAO;
import com.example.DAO.QualityFeedbackDAO;
import com.example.Helpers.PropertyUtils;
import com.example.VO.AdminVO;
import com.example.VO.QualityFeedbackVO;
import com.example.VO.QualityVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.example.report.ExcelReportGenerator;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class GenreateReportView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "GenreateReportView";
	WhatsUpUI ui;
	private AdminVO user;

	public GenreateReportView(WhatsUpUI ui) {
		this.ui=ui;
		 user=(AdminVO) ui.getSession().getAttribute("user");
		addComponent(init());
	}

	private VerticalLayout init() {
		VerticalLayout content = new VerticalLayout();
		ComboBox feedbackMonth = new ComboBox();
		content.setMargin(true);
		content.setSpacing(true);

		feedbackMonth.setCaption("Month");
		List<String> months = FeedbackDAO.getMonthList();
		for(String month:months)
			feedbackMonth.addItem(month);

		content.addComponent(feedbackMonth);
		content.setComponentAlignment(feedbackMonth, Alignment.MIDDLE_LEFT);

		Table viewFeedbackTable = new Table();

		viewFeedbackTable.setWidth("900px");
		viewFeedbackTable.setPageLength(15);

		IndexedContainer feedbackContainer = new IndexedContainer();

		viewFeedbackTable.addStyleName("SliderBar");
		viewFeedbackTable.setSelectable(true);

		Button generateReport = new Button("Generate Report");

		generateReport.addClickListener(e -> {
			generateReport(feedbackMonth);
			Window win = new Window("Enter Emails");
			win.setContent(new GetEmailsView(feedbackMonth,win));
			win.center();
			getUI().addWindow(win);


		});

		feedbackMonth.addValueChangeListener(e -> {
			viewFeedbackTable.removeAllItems();
			showFeedbacks(viewFeedbackTable, feedbackMonth);
			viewFeedbackTable.setPageLength(Math.min(feedbackContainer.size(), 10));
		});

		feedbackContainer.addContainerProperty("Employee Name", String.class, null);
		feedbackContainer.addContainerProperty("Quality Name", String.class, null);
		feedbackContainer.addContainerProperty("Satisfied or Not Satisfied", String.class, null);
		feedbackContainer.addContainerProperty("Comments", String.class, null);

		viewFeedbackTable.setColumnExpandRatio("Employee Name", 40);
		viewFeedbackTable.setColumnExpandRatio("Quality Name", 40);
		viewFeedbackTable.setColumnExpandRatio("Satisfied or Not Satisfied", 50);
		viewFeedbackTable.setColumnExpandRatio("Comments", 80);

		viewFeedbackTable.setContainerDataSource(feedbackContainer);

		viewFeedbackTable.setPageLength(Math.min(feedbackContainer.size(),10));
		content.addComponent(viewFeedbackTable);
		content.addComponent(generateReport);
		return content;

	}

	private void generateReport(ComboBox feedbackMonth) {
		ExcelReportGenerator excelReport = new ExcelReportGenerator(feedbackMonth);
		List<QualityVO> qualities = QualityDAO.getAllQualities();
		Properties prop = new PropertyUtils().getConfigProperties();
		float satisfactoryPercentage = Float.parseFloat((String) prop.get("SatisfactoryPercentage")) / 100;
		for (QualityVO quality : qualities) {
			float positiveResponses = 0;
			float negetiveResponses = 0;
			List<QualityFeedbackVO> feedbacks = QualityFeedbackDAO.getQualiyWiseFeedbacks(feedbackMonth,
					quality.getQualityName(),user.getTProject());
			for (QualityFeedbackVO qualityfeedback : feedbacks) {

				if (qualityfeedback.getSatisfyIndicator())
					positiveResponses++;
				else
					negetiveResponses++;
			}
			float percentage = (positiveResponses / (positiveResponses + negetiveResponses));
			if (percentage < satisfactoryPercentage) {
				excelReport.addRow(quality.getQualityName(), "Not Satisfactory", percentage);
			} else {
				excelReport.addRow(quality.getQualityName(), " Satisfactory", percentage);
			}
		}
		generateAreaWiseSheets(excelReport, qualities, feedbackMonth);
		excelReport.generateReport();

	}

	private void generateAreaWiseSheets(ExcelReportGenerator excelReport, List<QualityVO> qualities,
			ComboBox feedbackMonth) {
		String employeeName;
		String feedbackType;
		String projectName;
		for (QualityVO quality : qualities) {
			excelReport.GenerateAreaSheet(quality.getQualityName());
			List<QualityFeedbackVO> feedbacks = QualityFeedbackDAO.getQualiyWiseFeedbacks(feedbackMonth,
					quality.getQualityName(),user.getTProject());
			for (QualityFeedbackVO qualityfeedback : feedbacks) {
				employeeName = FeedbackDAO.getEmployeeName(qualityfeedback.getFeedbackId());
				projectName =  FeedbackDAO.getProjectName(qualityfeedback.getFeedbackId());
				feedbackType = qualityfeedback.getSatisfyIndicator() ? "Satisfied" : "Not Satisfied";
				excelReport.addRow(employeeName,projectName,feedbackType, qualityfeedback.getComment());
			}
		}
	}

	private void showFeedbacks(Table view_feedback, ComboBox feedback_month) {
		List<QualityFeedbackVO> feedbacks = QualityFeedbackDAO.getMonthWiseFeedbacks(feedback_month,user.getTProject());
		String employeeName;
		String qualityName;
		String feedbackType;
		int i = 1;
		for (QualityFeedbackVO feedback : feedbacks) {
			employeeName = FeedbackDAO.getEmployeeName(feedback.getFeedbackId());
			qualityName = QualityDAO.getQualityName(feedback.getQualityId());
			feedbackType = feedback.getSatisfyIndicator() ? "Satisfied" : "Not Satisfied";
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
		// TODO Auto-generated method stub

	}

}
