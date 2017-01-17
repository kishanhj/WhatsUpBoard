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
import com.example.constants.StringConstants;
import com.example.constants.ValidationConstants;
import com.example.validators.MonthValidator;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Start survey view
 */
public class StartSurveyView extends HorizontalLayout implements View {

	private static final long serialVersionUID = 1L;

	/**
	 * Name of this view in the Navigator
	 */
	public static final String NAME = "StartSurveyView";

	/**
	 * Holds the instance of current UI
	 */
	private WhatsUpUI ui;

	/**
	 * Holds information of currently Loggedin User
	 */
	private AdminVO user;

	/**
	 * Employee table
	 */
	Table employeelListTable;

	/**
	 * List of employees
	 */
	List<EmployeeVO> employees;

	/**
	 * Constructor with ui instance as parameter
	 *
	 * @param ui
	 *            WhatsUpUI
	 */
	public StartSurveyView(WhatsUpUI ui) {
		this.ui = ui;
		user = (AdminVO) ui.getSession().getAttribute("user");
		addComponent(buildLayout());

	}

	/**
	 * builds the layout
	 * @return VerticalLayout
	 */
	private VerticalLayout buildLayout() {

		VerticalLayout content = new VerticalLayout();
		ComboBox feedbackMonth = new ComboBox(StringConstants.MONTH);
		Properties prop1 = new PropertyUtils().getConfigProperties();
		String year = prop1.getProperty("year");
		addItems(feedbackMonth, year);

		feedbackMonth.setNullSelectionAllowed(false);
		feedbackMonth.setRequired(true);
		feedbackMonth.addValidator(new MonthValidator(ValidationConstants.MONTH_VALIDATOR, feedbackMonth,user.getTProject()));
		feedbackMonth.setValidationVisible(true);

		Button addEmployee = new Button("Add New Employee");
		addEmployee.setIcon(FontAwesome.USER_PLUS);
		addEmployee.addStyleName(StringConstants.STYLE_BUTTON_ICON);
		addEmployee.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		addEmployee.addStyleName(ValoTheme.BUTTON_LARGE);
		addEmployee.addClickListener(e -> {
			Window window = new Window("Add new Employee");
			window.setResizable(false);
			window.center();
			window.setContent(addEmployeeLayout(window));
			ui.addWindow(window);
		});

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.addComponents(feedbackMonth,addEmployee);
		buttons.setSizeFull();
		buttons.setComponentAlignment(feedbackMonth, Alignment.MIDDLE_LEFT);
		buttons.setComponentAlignment(addEmployee, Alignment.MIDDLE_RIGHT);

		content.addComponent(buttons);

		content.setSpacing(true);
		content.setMargin(true);
		content.setSizeUndefined();

		employees = EmployeeDAO.getEmployeeDetails();
	    employeelListTable = new Table("Employee List");
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

		loadEmployeeTable();
		content.addComponent(employeelListTable);
		content.setComponentAlignment(employeelListTable, Alignment.MIDDLE_CENTER);

		Button startSurvey = new Button("Start Survey");

		startSurvey.addClickListener(e -> {
			feedbackMonth.validate();
			for (EmployeeVO employee : employees) {
				AdminVO user = (AdminVO) ui.getSession().getAttribute("user");
				if (user.getTProject() == employee.getProjectId()) {
					Encoding.generatecodes(employee.getEmployeeId(), (String) feedbackMonth.getValue());
					FeedbackDAO.addFeedbackEntry(employee, (String) feedbackMonth.getValue());
					URL url = MailUtils.getUrl(employee.getEmployeeId(), (String) feedbackMonth.getValue());
					String mailBody = "Please complete the survey by clicking the below link<br><br>" + "<a href="
							+ url.toString() + ">here</a>";
					SendMail mailer = new SendMail(employee.getEmployeeEmailId(), mailBody);
					mailer.sendMail();
				}
			}
		});

		content.addComponent(startSurvey);

		return content;
	}

	/**
	 * loads the employee table data
	 */
	private void loadEmployeeTable() {
		employees = EmployeeDAO.getEmployeeDetails();
		employeelListTable.removeAllItems();
		int i = 1;
		String projectName;
		for (EmployeeVO employee : employees) {
			if (user.getTProject() == employee.getProjectId()) {
				projectName = ProjectDAO.getProjectName(employee.getProjectId());
				employeelListTable.addItem(new Object[] { employee.getEmployeeName(), employee.getEmployeeId(),
						projectName, employee.getEmployeeEmailId() }, i++);
			}
		}

	}

	/**
	 * Generates a layout to add an employee
	 * @param window
	 * @return
	 */
	private Component addEmployeeLayout(Window window) {
		VerticalLayout addProject = new VerticalLayout();
		addProject.setSpacing(true);
		addProject.setMargin(true);

		TextField employeeName =new TextField("Employee Name");
		employeeName.setRequired(true);
		TextField emailId =new TextField("Email ID");
		emailId.setRequired(true);
		TextField employeeId =new TextField("Employee ID");
		employeeId.setRequired(true);

		Button ok_button = new Button();
		ok_button.setCaption("OK");
		ok_button.addClickListener(e -> {
            if(!EmployeeDAO.exists(employeeId.getValue())){
            	EmployeeDAO.addEmployee(employeeName.getValue(),employeeId.getValue(),emailId.getValue(),user.getTProject());
			Notification.show("Succesfull added");
            }
            else
            Notification.show("Employee with this ID already exist");
            loadEmployeeTable();
			window.close();
	});
		addProject.addComponents(employeeName,employeeId,emailId,ok_button);
		return addProject;
	}

	/**
	 * Adds items to month ComboBox
	 *
	 * @param feedbackMonth
	 *            ComboBox
	 * @param year
	 *            Year from Properties file
	 */
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

	}

}
