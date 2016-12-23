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

	Grid FeedbackDisp;

	Navigator nav;

	private VerticalLayout rootLayout = new VerticalLayout();

	private HorizontalSplitPanel hSplitPanel;


	public AdminView(WhatsUpUI ui) {

		hSplitPanel = new HorizontalSplitPanel();
		hSplitPanel.setSizeFull();
		hSplitPanel.setSplitPosition(20);
		rootLayout.addComponents(hSplitPanel);

		addComponent(init());
		ui.setContent(this);

	}

	@SuppressWarnings("unchecked")
private VerticalLayout init() {
		FeedbackDisp = new Grid();
		FeedbackDisp.setWidth(100f, Unit.PERCENTAGE);
		FeedbackDisp.setSelectionMode(SelectionMode.MULTI);
		FeedbackDisp.setEditorCancelCaption("Delete");


		VerticalLayout Content = new VerticalLayout();
		ComboBox month = new ComboBox("Month");
		month.addItem("January");
		month.addItem("February");
		month.addItem("March");
		month.addItem("April");
		month.setNullSelectionAllowed(false);
		month.setRequired(true);
		month.addValidator(new MonthValidator(ValidationConstants.MONTH_VALIDATOR, month));
		month.setValidationVisible(true);
		Content.addComponent(month);

		Content.setSpacing(true);
		Content.setMargin(true);
		Content.setSizeUndefined();
		Content.setComponentAlignment(month, Alignment.MIDDLE_RIGHT);

		Table employeelListTable = new Table("Employee List");
		employeelListTable.setWidth("800px");
		employeelListTable.setHeight("800px");

		IndexedContainer container = new IndexedContainer();

		employeelListTable.addStyleName("SliderBar");
		employeelListTable.setCaption("Employee List");

		container.addContainerProperty("Employee Name", String.class, null);
		container.addContainerProperty("Employee Id", String.class, null);
		container.addContainerProperty("Project", String.class, null);
		container.addContainerProperty("Email Id", String.class, null);

		employeelListTable.setContainerDataSource(container);

		employeelListTable.setColumnExpandRatio("Employee Name", 40);
		employeelListTable.setColumnExpandRatio("Employee Id", 30);
		employeelListTable.setColumnExpandRatio("Project", 30);
		employeelListTable.setColumnExpandRatio("Email Id", 80);



		Content.addComponent(employeelListTable);
		Content.setComponentAlignment(employeelListTable, Alignment.MIDDLE_CENTER);

		List<EmployeeVO> employees=EmployeeDAO.getEmployeeDetails();
        int i=1;
        String projectName;
        for(EmployeeVO employee:employees){
        	projectName=ProjectDAO.getProjectName(employee.getProjectId());
        	employeelListTable.addItem(new Object[] { employee.getEmployeeName(), employee.getEmployeeId(), projectName, employee.getEmployeeEmailId() }, i++);
        }
		Content.addComponent(employeelListTable);
		Content.setComponentAlignment(employeelListTable, Alignment.MIDDLE_CENTER);

		Button startSurvey = new Button("Start Survey");

		startSurvey.addClickListener(e ->{
			month.validate();
			for(EmployeeVO employee:employees){
	        Encoding.generatecodes(employee.getEmployeeId(), (String)month.getValue());
	        FeedbackDAO.addFeedbackEntry(employee, (String)month.getValue());
	        URL url=MailUtils.getUrl(employee.getEmployeeId(), (String)month.getValue());
	        System.out.println((String)month.getValue());
	        String content = "Please complete the survey by clicking the below link<br><br>"+"<a href="+url.toString()+">here</a>";
	        System.out.println(url);
	        new SendMail(employee.getEmployeeEmailId(),content);
			}
			});

		Content.addComponent(startSurvey);

		rootLayout.addComponent(Content);

////////////////////////////////////////////////////////////////////////////////////////////////////

         ComboBox feedbackMonth=new ComboBox();

		feedbackMonth.setCaption("Month");
		feedbackMonth.addItem("January");
		feedbackMonth.addItem("February");
		feedbackMonth.addItem("March");

		Content.addComponent(feedbackMonth);
		Content.setComponentAlignment(feedbackMonth, Alignment.MIDDLE_CENTER);
		rootLayout.addComponent(Content);

		Table viewFeedbackTable=new Table();

		viewFeedbackTable.setWidth("900px");
		viewFeedbackTable.setHeight("275px");

		IndexedContainer container1 = new IndexedContainer();

		viewFeedbackTable.addStyleName("SliderBar");
		viewFeedbackTable.setCaption("View Feedback");
		viewFeedbackTable.setSelectable(true);

		Button generateReport =new Button("Generate Report");

		generateReport.addClickListener(e ->{
			ExcelReportGenerator excelReport=new ExcelReportGenerator(feedbackMonth);
			List<QualityVO> qualities=QualityDAO.getAllQualities();
			Properties prop=new PropertyUtils().getConfigProperties();
			float satisfactoryPercentage=Float.parseFloat((String)prop.get("SatisfactoryPercentage"))/100;
			for(QualityVO quality:qualities){
				float positiveResponses=0;
				float negetiveResponses=0;
			 List<QualityFeedbackVO> feedbacks=QualityFeedbackDAO.getQualiyWiseFeedbacks(feedbackMonth,quality.getQualityName());
			 for(QualityFeedbackVO qualityfeedback:feedbacks){
				 if(qualityfeedback.getSatisfyIndicator())
					 positiveResponses++;
				 else
					 negetiveResponses++;
			 }
			 float percentage=(positiveResponses/(positiveResponses+negetiveResponses));
			 if(percentage < satisfactoryPercentage){
				excelReport.addRow(quality.getQualityName(), "Not Satisfactory", percentage);
				 System.out.println("Negative"+(positiveResponses/(positiveResponses+negetiveResponses)));
			 }
			 else {
				 excelReport.addRow(quality.getQualityName(), " Satisfactory", percentage);
				 System.out.println("Positive"+(positiveResponses/(positiveResponses+negetiveResponses)));
			 }
			}
			excelReport.generateReport();
			});

		feedbackMonth.addValueChangeListener(e ->{
			viewFeedbackTable.removeAllItems();
			showFeedbacks(viewFeedbackTable, feedbackMonth);
		});

		container1.addContainerProperty("Employee Name", String.class, null);
		container1.addContainerProperty("Quality Name", String.class, null);
		container1.addContainerProperty("Satisfied or Not Satisfied", String.class, null);
		container1.addContainerProperty("Comments", String.class, null);

		viewFeedbackTable.setColumnExpandRatio("Employee Name", 40);
		viewFeedbackTable.setColumnExpandRatio("Quality Name", 40);
		viewFeedbackTable.setColumnExpandRatio("Satisfied or Not Satisfied", 50);
		viewFeedbackTable.setColumnExpandRatio("Comments", 80);



			viewFeedbackTable.setContainerDataSource(container1);
			Content.addComponent(viewFeedbackTable);

		rootLayout.addComponent(Content);
		Content.setComponentAlignment(viewFeedbackTable, Alignment.MIDDLE_CENTER);



			viewFeedbackTable.setContainerDataSource(container1);

		Content.addComponents(viewFeedbackTable,generateReport);


		rootLayout.addComponent(Content);
		Content.setComponentAlignment(viewFeedbackTable, Alignment.MIDDLE_CENTER);

		MenuView menu = new MenuView();
		menu.setSizeUndefined();
		hSplitPanel.setFirstComponent(menu);
		hSplitPanel.setSecondComponent(Content);


		rootLayout.addComponents(hSplitPanel);

		return rootLayout;
	}

	private void showFeedbacks(Table view_feedback,ComboBox feedback_month ){
		 List<QualityFeedbackVO> feedbacks=QualityFeedbackDAO.getMonthWiseFeedbacks(feedback_month);
         String employeeName;
         String qualityName;
         String feedbackType;
         int i=1;
		 for(QualityFeedbackVO feedback:feedbacks){
	            employeeName=FeedbackDAO.getemployeeName(feedback.getFeedbackId());
	            qualityName=QualityDAO.getQualityName(feedback.getQualityId());
	            feedbackType =feedback.getSatisfyIndicator()?"Satisfied":"Not Satisfied";
	        	view_feedback.addItem(new Object[] { employeeName, qualityName,feedbackType ,feedback.getComment() }, i++);
	        	ArrayList<String> varialble = new ArrayList<String>();
	        	if(varialble.size()!=0){
	        		if(!varialble.contains(employeeName)){
	        		varialble.add(employeeName);
	        	}}


	        }
	}



	@Override
	public void enter(ViewChangeEvent event) {

	}

}
