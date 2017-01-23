package com.example.Views;

import java.net.URL;
import java.util.List;

import com.example.DAO.EmployeeDAO;
import com.example.DAO.FeedbackDAO;
import com.example.DAO.LinkCodesDAO;
import com.example.DAO.QualityDAO;
import com.example.DAO.QualityFeedbackDAO;
import com.example.Mailer.MailUtils;
import com.example.Mailer.SendMail;
import com.example.VO.AdminVO;
import com.example.VO.EmployeeVO;
import com.example.VO.QualityFeedbackVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.example.constants.IntegerConstants;
import com.example.constants.StringConstants;
import com.example.constants.ValidationConstants;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Generates View Feedback View
 */
public class ViewFeedbackView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "ViewFeedbackView";
	WhatsUpUI ui;
	private AdminVO user;

	public enum status {
		completed, link_sent, link_not_sent
	};

	public ViewFeedbackView(WhatsUpUI ui) {
		this.ui = ui;
		user = (AdminVO) ui.getSession().getAttribute("user");
		addComponent(init());

	}

	/**
	 * Initializes to build the layout
	 *
	 * @return
	 */
	private VerticalLayout init() {
		VerticalLayout content = new VerticalLayout();
		ComboBox feedbackMonth = new ComboBox();
		feedbackMonth.setNullSelectionAllowed(false);
		content.setMargin(true);
		content.setSpacing(true);

		feedbackMonth.setCaption("Month");
		List<String> months = FeedbackDAO.getMonthList(user.getTProject());
		for (String month : months)
			feedbackMonth.addItem(month);

		content.addComponent(feedbackMonth);
		content.setComponentAlignment(feedbackMonth, Alignment.MIDDLE_LEFT);

		Table viewEmployeeStatusTable = new Table();

		viewEmployeeStatusTable.setWidth("900px");
		viewEmployeeStatusTable.setPageLength(15);

		IndexedContainer feedbackContainer = new IndexedContainer();

		ProgressBar completedFeedbacks = new ProgressBar();
		completedFeedbacks.setWidth("900px");

		completedFeedbacks.addContextClickListener(e -> {
			float count = completedFeedbacks.getValue() * 100;
			int employeesLeft = (int) (EmployeeDAO.getEmployeeCount(user.getTProject())
					* (1 - completedFeedbacks.getValue()));
			Notification.show(count + "% completed,Number of employees left: " + employeesLeft);
		});

		viewEmployeeStatusTable.addStyleName("SliderBar");
		viewEmployeeStatusTable.setSelectable(true);

		feedbackMonth.addValueChangeListener(e -> {
			viewEmployeeStatusTable.removeAllItems();
			showEmployeeTable(viewEmployeeStatusTable, feedbackMonth);
			List<EmployeeVO> employees = EmployeeDAO.getEmployeeDetails(user.getTProject());
			float leftcount=0;
			for (EmployeeVO employee : employees) {
					int feedbackId = FeedbackDAO.getFeedbackId(employee.getEmployeeId(), (String) feedbackMonth.getValue());
					if (!QualityFeedbackDAO.exists(feedbackId)){
						leftcount++;
					}
			}
			viewEmployeeStatusTable.setPageLength(Math.min(feedbackContainer.size(), 10));
			completedFeedbacks.setValue((float) 1-(leftcount/employees.size()));
			if (completedFeedbacks.getValue() == IntegerConstants.ONE)
				LinkCodesDAO.deleteCode(feedbackMonth);
		});

		Button resendLinks = new Button("Resend Links");
		resendLinks.addClickListener(e ->{
			if(feedbackMonth.getValue() == null){
				Notification.show(ValidationConstants.ERROR,"Please select a month", Type.ERROR_MESSAGE);
				return ;
			}
			List<EmployeeVO> employees = EmployeeDAO.getEmployeeDetails();
			int feedbackId;
			for (EmployeeVO employee : employees) {
				if (user.getTProject() == employee.getProjectId()) {
					feedbackId = FeedbackDAO.getFeedbackId(employee.getEmployeeId(), (String) feedbackMonth.getValue());
					if (!QualityFeedbackDAO.exists(feedbackId)){
						URL url=MailUtils.getUrl(employee.getEmployeeId(), (String)feedbackMonth.getValue());
						String mailBody = "Please complete the survey by clicking the below link<br><br>" + "<a href="
								+ url.toString() + ">here</a>";
						SendMail mailer = new SendMail(employee.getEmployeeEmailId(), mailBody);
						if(!mailer.sendMail()){
							LinkCodesDAO.deleteCode(employee.getEmployeeId());
						}
					}
				}
			}
			Notification.show("Mails Sent",Type.TRAY_NOTIFICATION);
		});

		feedbackContainer.addContainerProperty("Employee Name", String.class, null);
		feedbackContainer.addContainerProperty("Status", status.class, null);
		feedbackContainer.addContainerProperty("", HorizontalLayout.class, null);

		viewEmployeeStatusTable.setColumnExpandRatio("Employee Name", 40);
		viewEmployeeStatusTable.setColumnExpandRatio("Status", 40);
		viewEmployeeStatusTable.setColumnExpandRatio("", 50);

		viewEmployeeStatusTable.setPageLength(Math.min(feedbackContainer.size(), 10));
		viewEmployeeStatusTable.setContainerDataSource(feedbackContainer);
		content.addComponents(completedFeedbacks, viewEmployeeStatusTable,resendLinks);
		return content;

	}

	/**
	 * Displays values in the table
	 *
	 * @param statusTable
	 * @param feedback_month
	 */
	private void showEmployeeTable(Table statusTable, ComboBox feedback_month) {
		List<EmployeeVO> employees = EmployeeDAO.getEmployeeDetails();
		status currentStatus;
		HorizontalLayout viewFeedback;
		int feedbackId;
		int i = 0;
		for (EmployeeVO employee : employees) {
			if (user.getTProject() == employee.getProjectId()) {
				viewFeedback = generateViewButton(employee, feedback_month);

				feedbackId = FeedbackDAO.getFeedbackId(employee.getEmployeeId(), (String) feedback_month.getValue());
				if (feedbackId == 0)
					currentStatus = status.link_not_sent;
				else if (QualityFeedbackDAO.exists(feedbackId))
					currentStatus = status.completed;
				else
					currentStatus = status.link_sent;
				if(currentStatus != status.completed)
					viewFeedback.getComponent(0).setEnabled(false);
				else
					viewFeedback.getComponent(1).setEnabled(false);
				statusTable.addItem(new Object[] { employee.getEmployeeName(), currentStatus, viewFeedback }, i++);
			}
		}
	}

	private HorizontalLayout generateViewButton(EmployeeVO employee, ComboBox feedback_month) {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSpacing(true);
		Button viewFeedback = new Button("VIEW FEEDBACK");
		Button resendLink = new Button("RESEND LINK");
		viewFeedback.setData(employee);
		resendLink.setData(employee);

		viewFeedback.addClickListener(e -> {
			EmployeeVO employeeVo = (EmployeeVO) e.getButton().getData();
			String month = (String) feedback_month.getValue();
			int feedbackid = FeedbackDAO.getFeedbackIdFromName(employeeVo.getEmployeeName(), month);
			Window window = new Window();
			window.setResizable(false);
			window.center();
			window.setContent(showFeedbackLayout(window, employeeVo.getEmployeeName(), feedbackid));
			ui.addWindow(window);
		});
		resendLink.addClickListener(e -> {
			EmployeeVO employeeVo = (EmployeeVO) e.getButton().getData();
			URL url=MailUtils.getUrl(employeeVo.getEmployeeId(), (String)feedback_month.getValue());
			String mailBody = "Please complete the survey by clicking the below link<br><br>" + "<a href="
					+ url.toString() + ">here</a>";
			SendMail mailer = new SendMail(employee.getEmployeeEmailId(), mailBody);
			if(!mailer.sendMail()){
				LinkCodesDAO.deleteCode(employee.getEmployeeId());
			}else{
            Notification.show("Mail Sent", Type.HUMANIZED_MESSAGE);
			}});
		layout.addComponents(viewFeedback, resendLink);
		return layout;
	}

	private Component showFeedbackLayout(Window window, String employeeName, int feedbackid) {
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		TextField employeename = new TextField("Employee name", employeeName);
		employeename.setReadOnly(true);
		IndexedContainer feedbackContainer = buildContainer();
		Table view_feedback = BuildTable(feedbackContainer);
		showFeedbacks(view_feedback, feedbackid);
		mainLayout.addComponents(employeename, view_feedback);
		return mainLayout;
	}

	/**
	 * Builds the container
	 *
	 * @return IndexedContainer
	 */
	private IndexedContainer buildContainer() {
		IndexedContainer feedbackContainer = new IndexedContainer();
		feedbackContainer.addContainerProperty(StringConstants.QUALITY_NAME, String.class, null);
		feedbackContainer.addContainerProperty(StringConstants.IS_SATISFIED, String.class, null);
		feedbackContainer.addContainerProperty(StringConstants.COMMENTS, String.class, null);
		return feedbackContainer;
	}

	/**
	 * Builds the table
	 *
	 * @param feedbackContainer
	 *            A IndexedContainer
	 * @return Table
	 */
	private Table BuildTable(IndexedContainer feedbackContainer) {
		Table viewFeedbackTable = new Table();
		viewFeedbackTable.setWidth(StringConstants.NINE_HUNDRED_PX);
		viewFeedbackTable.setPageLength(15);
		viewFeedbackTable.setColumnExpandRatio(StringConstants.QUALITY_NAME, 40);
		viewFeedbackTable.setColumnExpandRatio(StringConstants.IS_SATISFIED, 50);
		viewFeedbackTable.setColumnExpandRatio(StringConstants.COMMENTS, 80);

		viewFeedbackTable.setContainerDataSource(feedbackContainer);

		viewFeedbackTable.setPageLength(Math.min(feedbackContainer.size(), 10));
		return viewFeedbackTable;
	}

	/**
	 * Displays feedbacks in the table
	 *
	 * @param view_feedback
	 * @param feedback_month
	 */
	private void showFeedbacks(Table view_feedback, int feedbackId) {
		List<QualityFeedbackVO> feedbacks = QualityFeedbackDAO.getFeedbacks(feedbackId);
		String qualityName;
		String feedbackType;
		int i = 1;
		for (QualityFeedbackVO feedback : feedbacks) {
			qualityName = QualityDAO.getQualityName(feedback.getQualityId());
			feedbackType = feedback.getSatisfyIndicator() ? StringConstants.SATISFIED : StringConstants.NOT_SATISFIED;
			view_feedback.addItem(new Object[] {qualityName, feedbackType, feedback.getComment() }, i++);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
