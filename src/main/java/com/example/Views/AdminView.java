package com.example.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;

import java.net.URL;
import java.util.List;

import com.example.DAO.EmployeeDAO;
import com.example.DAO.FeedbackDAO;
import com.example.DAO.ProjectDAO;
import com.example.DAO.QualityDAO;
import com.example.DAO.QualityFeedbackDAO;
import com.example.Mailer.Encoding;
import com.example.Mailer.MailUtils;
import com.example.Mailer.SendMail;
import com.example.VO.EmployeeVO;
import com.example.VO.QualityFeedbackVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.example.constants.ValidationConstants;
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

		Table employee_list = new Table("Employee List");
		employee_list.setWidth("800px");
		employee_list.setHeight("800px");

		IndexedContainer container = new IndexedContainer();

		employee_list.addStyleName("SliderBar");
		employee_list.setCaption("Employee List");

		container.addContainerProperty("Employee Name", String.class, null);
		container.addContainerProperty("Employee Id", String.class, null);
		container.addContainerProperty("Project", String.class, null);
		container.addContainerProperty("Email Id", String.class, null);

		employee_list.setContainerDataSource(container);

		employee_list.setColumnExpandRatio("Employee Name", 40);
		employee_list.setColumnExpandRatio("Employee Id", 30);
		employee_list.setColumnExpandRatio("Project", 30);
		employee_list.setColumnExpandRatio("Email Id", 80);



		Content.addComponent(employee_list);
		Content.setComponentAlignment(employee_list, Alignment.MIDDLE_CENTER);

		List<EmployeeVO> employees=EmployeeDAO.getEmployeeDetails();
        int i=1;
        String projectName;
        for(EmployeeVO employee:employees){
        	projectName=ProjectDAO.getProjectName(employee.getProjectId());
        	employee_list.addItem(new Object[] { employee.getEmployeeName(), employee.getEmployeeId(), projectName, employee.getEmployeeEmailId() }, i++);
        }
		Content.addComponent(employee_list);
		Content.setComponentAlignment(employee_list, Alignment.MIDDLE_CENTER);

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

         ComboBox feedback_month=new ComboBox();

		feedback_month.setCaption("Month");
		feedback_month.addItem("January");
		feedback_month.addItem("February");
		feedback_month.addItem("March");

		Content.addComponent(feedback_month);
		Content.setComponentAlignment(feedback_month, Alignment.MIDDLE_CENTER);
		rootLayout.addComponent(Content);

		Table view_feedback=new Table();

		view_feedback.setWidth("900px");
		view_feedback.setHeight("275px");

		IndexedContainer container1 = new IndexedContainer();

		view_feedback.addStyleName("SliderBar");
		view_feedback.setCaption("View Feedback");
		view_feedback.setSelectable(true);


		feedback_month.addValueChangeListener(e ->{
			view_feedback.removeAllItems();
			showFeedbacks(view_feedback, feedback_month);
			//Page.getCurrent().reload();
		});

		container1.addContainerProperty("Employee Name", String.class, null);
		container1.addContainerProperty("Quality Name", String.class, null);
		container1.addContainerProperty("Satisfied or Not Satisfied", String.class, null);
		container1.addContainerProperty("Comments", String.class, null);

		view_feedback.setColumnExpandRatio("Employee Name", 40);
		view_feedback.setColumnExpandRatio("Quality Name", 40);
		view_feedback.setColumnExpandRatio("Satisfied or Not Satisfied", 50);
		view_feedback.setColumnExpandRatio("Comments", 80);



			view_feedback.setContainerDataSource(container1);
			Content.addComponent(view_feedback);

		rootLayout.addComponent(Content);
		Content.setComponentAlignment(view_feedback, Alignment.MIDDLE_CENTER);



			view_feedback.setContainerDataSource(container1);

		Content.addComponent(view_feedback);


		rootLayout.addComponent(Content);
		Content.setComponentAlignment(view_feedback, Alignment.MIDDLE_CENTER);

		MenuView menu = new MenuView();
		menu.setSizeUndefined();
		hSplitPanel.setFirstComponent(menu);
		hSplitPanel.setSecondComponent(Content);


		rootLayout.addComponents(hSplitPanel);

		return rootLayout;
	}

	private void showFeedbacks(Table view_feedback,ComboBox feedback_month ){
		 List<QualityFeedbackVO> feedbacks=QualityFeedbackDAO.getMonthWiseFeedbacks(feedback_month);
		 System.out.println(feedbacks.size());
         String employeeName;
         String qualityName;
         String feedbackType;
         int i=1;
		 for(QualityFeedbackVO feedback:feedbacks){
	            employeeName=FeedbackDAO.getemployeeName(feedback.getFeedbackId());
	            qualityName=QualityDAO.getQualityName(feedback.getQualityId());
	            feedbackType =feedback.getSatisfyIndicator()?"Satisfied":"Not Satisfied";
	        	view_feedback.addItem(new Object[] { employeeName, qualityName,feedbackType ,feedback.getComment() }, i++);
	        }
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
