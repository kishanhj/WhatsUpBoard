package com.example.Views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.DAO.EmployeeDAO;
import com.example.DAO.FeedbackDAO;
import com.example.DAO.LinkCodesDAO;
import com.example.DAO.ProjectDAO;
import com.example.DAO.QualityDAO;
import com.example.DAO.QualityFeedbackDAO;
import com.example.Helpers.Utils;
import com.example.VO.QualityVO;
import com.example.VO.EmployeeVO;
import com.example.VO.QualityFeedbackVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnResizeEvent;
import com.vaadin.ui.Table.ColumnResizeListener;

public class FeedbackFormView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	/**
	 * Name of the View in the navigator
	 */
	public static String NAME = "FeedBackFormview";

	/**
	 * Holds the instance of current UI
	 */
	WhatsUpUI ui;

	TextField name;

	Navigator navigator;

	TextField employeeIdTextField;

	TextField emailId;

	ComboBox monthField;

	ComboBox project;

	EmployeeVO employee = new EmployeeVO();

	String employeeId;

	String month;

	/**
	 * holds the list of all the QualityVO's
	 */
	List<QualityVO> qualities;

	/**
	 * Map of all quality Buttons
	 */
	Map<String, Button> qualityButtons = new HashMap<String, Button>();

	/**
	 * Map of all quality comment TextAreas
	 */
	Map<String, TextArea> qualityReason = new HashMap<String, TextArea>();

	/**
	 * Map of all quality Images
	 */
	Map<String, Resource> qualityImage = new HashMap<String, Resource>();

	public FeedbackFormView(WhatsUpUI ui) {
		this.ui = ui;
		navigator = ui.getNavigator();
		qualities = QualityDAO.getAllQualities();
		setSizeUndefined();
		Component feedBackForm = BuildForm();
		addComponents(feedBackForm);
		setComponentAlignment(feedBackForm, Alignment.MIDDLE_CENTER);
	}

	public FeedbackFormView(WhatsUpUI ui, String employeeId, String month) {
		this.employee = EmployeeDAO.getEmployee(employeeId);
		this.month = month;
		this.employeeId = employeeId;
		this.ui = ui;
		navigator = ui.getNavigator();
		qualities = QualityDAO.getAllQualities();
		Component feedBackForm = BuildForm();
		setSizeFull();
		addComponents(feedBackForm);
		setComponentAlignment(feedBackForm, Alignment.MIDDLE_CENTER);
	}

	/**
	 * Builds the primary layout and adds components to it
	 *
	 * @return Component : which is the MainLayout
	 */
	private Component BuildForm() {

		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();

		final HorizontalLayout headerLayout = new HorizontalLayout();
		HorizontalLayout logoBar = buildLogoBar();
		headerLayout.addStyleName("titlepanel");
		headerLayout.setSizeFull();
		headerLayout.setWidth("100%");
		headerLayout.setHeight("40px");
		headerLayout.addComponents(logoBar);
		headerLayout.setExpandRatio(logoBar, 1);

		HorizontalLayout textFields = buildTextFields();
		textFields.addStyleName("textStyle");

		VerticalLayout vlayout = new VerticalLayout();
		vlayout.addComponents(headerLayout, textFields);
		vlayout.setComponentAlignment(textFields, Alignment.TOP_CENTER);
		vlayout.setExpandRatio(headerLayout, 1);
		vlayout.setExpandRatio(textFields, 4);


		Table feedBackForm = generateTable();

		final VerticalSplitPanel HeaderPanel = new VerticalSplitPanel();
		HeaderPanel.setSizeFull();
		HeaderPanel.setSplitPosition(25f, Unit.PERCENTAGE);
		HeaderPanel.setLocked(true);
		HeaderPanel.setFirstComponent(vlayout);
		HeaderPanel.setSecondComponent(feedBackForm);
		mainLayout.addComponents(HeaderPanel);
		return mainLayout;
	}

	/**
	 * Builds the LogoBar
	 *
	 * @return Horizontal Layout
	 */
	private HorizontalLayout buildLogoBar() {
		HorizontalLayout logoBar = new HorizontalLayout();
		Image brillioLogo = new Image(null, new ThemeResource("B1.png"));

		Label title = new Label("<center>WhatsUp Board Survey<center>");
		title.setContentMode(ContentMode.HTML);
		title.addStyleName(ValoTheme.LABEL_H2);
		title.addStyleName(ValoTheme.LABEL_BOLD);

		logoBar.setSizeFull();
		HorizontalLayout buttonsLayout = generateButtons();
		logoBar.addComponents(brillioLogo, title, buttonsLayout);
		 logoBar.setComponentAlignment(title, Alignment.BOTTOM_CENTER);
		logoBar.setExpandRatio(title, 60);
		logoBar.setExpandRatio(brillioLogo, 20);
		logoBar.setExpandRatio(buttonsLayout, 20);
		Responsive.makeResponsive(logoBar);
		return logoBar;

	}

	/**
	 * Generates submit and clear buttons
	 *
	 * @return HorizontalLayout
	 */
	private HorizontalLayout generateButtons() {

		Button submit = new Button("Submit");
		submit.setClickShortcut(KeyCode.ENTER);

		Button clear = new Button("Clear");

		submit.addClickListener(e -> {
			try {
				List<QualityFeedbackVO> qualityWiseFeedbacks;
				validateAllFields();
				qualityWiseFeedbacks = generateQualityWiseFeedback();
				boolean flag = QualityFeedbackDAO.addFeedbacks(qualityWiseFeedbacks);
				if (flag) {
					LinkCodesDAO.deleteCode(employeeIdTextField);
					Notification.show("Feedback was captured succesfully");
					ui.setContent(new SuccessView());
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

		});

		clear.addClickListener(e -> {
			ui.setContent(new FeedbackFormView(ui, employeeId, month));
		});

		HorizontalLayout buttons = new HorizontalLayout(submit, clear);
		buttons.setMargin(true);
		buttons.setSpacing(true);
		buttons.setComponentAlignment(submit, Alignment.TOP_RIGHT);
		buttons.setComponentAlignment(clear, Alignment.TOP_LEFT);
		buttons.setSizeFull();

		return buttons;

	}

	/**
	 * Invokes Validations on all fields
	 */
	private void validateAllFields() {
		employeeIdTextField.validate();
		monthField.validate();
		project.validate();
		for (QualityVO key : qualities) {
			TextArea reason = qualityReason.get(key.getQualityName());
			reason.validate();
		}

	}

	/**
	 * Captures feedback for all qualities form user
	 *
	 * @return List of QualityFeedbackVO
	 * @throws ClassNotFoundException
	 */
	private List<QualityFeedbackVO> generateQualityWiseFeedback() throws ClassNotFoundException {
		int feedbackId = FeedbackDAO.getFeedbackId(employeeIdTextField.getValue(), (String) monthField.getValue());
		List<QualityFeedbackVO> qualityWiseFeedbacks = new ArrayList<QualityFeedbackVO>();
		QualityFeedbackVO qualityFeedback;
		for (QualityVO quality : qualities) {
			Button qualityBtn = qualityButtons.get(quality.getQualityName());
			Boolean satisfyIndicator = Utils.captionToBooleanConvertor(qualityBtn);
			TextArea reason = qualityReason.get(quality.getQualityName());
			qualityFeedback = new QualityFeedbackVO(feedbackId, satisfyIndicator, reason.getValue(),
					quality.getQualityId());
			qualityWiseFeedbacks.add(qualityFeedback);
		}
		return qualityWiseFeedbacks;
	}

	/**
	 * Generates a Vaadin table and configures it
	 *
	 * @return Vaadin Table
	 */
	private Table generateTable() {

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);

		Table feedbackTable = new Table();
		feedbackTable.setSizeFull();
		// feedbackTable.setSizeFull();
		feedbackTable.setPageLength(feedbackTable.size());
		ConfigTable(feedbackTable);
		addRows(feedbackTable);

		return feedbackTable;
	}

	/**
	 * Adds rows to the table
	 *
	 * @param feedbackTable
	 */
	private void addRows(Table feedbackTable) {
		for (QualityVO quality : qualities) {
			createFields(feedbackTable, quality.getQualityName(), quality.getQualityDescription(),
					quality.getQualityId());
		}
	}

	// private void setImageresources() {
	// List<Resource> Images = new ArrayList<Resource>();
	//
	// Resource LeadershipTouch=new ThemeResource("leadership.jpg");
	// Resource Communication=new ThemeResource("communication.jpg");
	// Resource TimelyRecognition=new ThemeResource("timlyrecognition2.jpg");
	// Resource Learning=new ThemeResource("learning.jpg");
	// Resource FeedForward=new ThemeResource("feedforward1.jpg");
	// Resource HRResponsiveness=new ThemeResource("HR.jpg");
	//
	//
	// Images.add( LeadershipTouch);
	// Images.add(Communication);
	// Images.add(TimelyRecognition);
	// Images.add(Learning);
	// Images.add(FeedForward);
	// Images.add(HRResponsiveness);
	//
	// int count=1;
	//
	// for(QualityVO quality:qualities){
	// qualityImage.put(quality.getQualityName(), Images.get(count++));
	// }
	//
	//
	// }

	/**
	 * creates components to be added to the table
	 *
	 * @param feedbackTable
	 * @param qualityName
	 * @param qualityDesc
	 * @param rowNumber
	 */
	private void createFields(Table feedbackTable, String qualityName, String qualityDesc, int rowNumber) {
		Button qualityButton = new Button("Satisfied");
		TextArea reason = new TextArea();
		ConfigButtonAndText(qualityButton, reason);
		qualityButtons.put(qualityName, qualityButton);
		qualityReason.put(qualityName, reason);
		feedbackTable.addItem(new Object[] { qualityName, qualityDesc, qualityButton, reason }, rowNumber);
	}

	/**
	 * Configures the table button and textarea to required format
	 *
	 * @param button
	 * @param text
	 */
	private void ConfigButtonAndText(Button button, TextArea text) {

		text.setWidth(390, Unit.PIXELS);
		button.setIcon(new ThemeResource("bad.png"));
		button.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		button.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		button.addStyleName(ValoTheme.BUTTON_HUGE);
		button.addClickListener(e -> Utils.imageToggler(button, text));

	}

	/**
	 * Configure the table columns
	 *
	 * @param feedbackTable
	 */
	private void ConfigTable(Table feedbackTable) {
		feedbackTable.addContainerProperty("Area", String.class, null);
		feedbackTable.addContainerProperty("Description", String.class, null);
		feedbackTable.addContainerProperty("Satisfied", Button.class, null);
		feedbackTable.addContainerProperty("Reason", TextArea.class, null);

		feedbackTable.setColumnWidth("Area", 250);
		feedbackTable.setColumnWidth("Description", 490);
		feedbackTable.setColumnWidth("Satisfied", 190);
		feedbackTable.setColumnWidth("Reason", 400);

		feedbackTable.setColumnAlignment("Area", Align.CENTER);
		feedbackTable.setColumnAlignment("Description", Align.CENTER);
		feedbackTable.setColumnAlignment("Satisfied", Align.CENTER);
		feedbackTable.setColumnAlignment("Reason", Align.CENTER);

		feedbackTable.setColumnExpandRatio("Area", 9);
		feedbackTable.setColumnExpandRatio("Description", 20);
		feedbackTable.setColumnExpandRatio("Satisfied",8);
		feedbackTable.setColumnExpandRatio("Reason", 20);
		feedbackTable.setColumnReorderingAllowed(false);


		feedbackTable.addColumnResizeListener(new ColumnResizeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void columnResize(ColumnResizeEvent event) {

				feedbackTable.setColumnWidth(event.getPropertyId(), event.getPreviousWidth());

			}
		});

		feedbackTable.setColumnHeaders("Area", "Description", "Feedback", "Reason");
		feedbackTable.addStyleName(ValoTheme.TABLE_COMPACT);
		feedbackTable.addStyleName(Reindeer.TABLE_STRONG);

	}

	/**
	 * Builds the name,employee,email,month,project textfields
	 *
	 * @return Horizontal Layout
	 */
	private HorizontalLayout buildTextFields() {
		HorizontalLayout fieldLayout = new HorizontalLayout();
		fieldLayout.setSpacing(true);

		name = new TextField("Name");
		name.setValue(employee.getEmployeeName());
		name.setReadOnly(true);

		employeeIdTextField = new TextField("EmployeeID");
		employeeIdTextField.setValue(employee.getEmployeeId());
		employeeIdTextField.setReadOnly(true);

		monthField = new ComboBox("Month");
		monthField.setRequired(true);
		monthField.addItem(month);
		monthField.setValue(month);
		monthField.setNullSelectionAllowed(false);
		monthField.setReadOnly(true);

		project = new ComboBox("Project");
		project.setRequired(true);
		project.setNullSelectionAllowed(false);
		String projectName = ProjectDAO.getProjectName(employee.getProjectId());
		List<String> projects = ProjectDAO.getAllProjects();
		for (String projectname : projects) {
			project.addItem(projectname);
		}
		project.setValue(projectName);
		project.setReadOnly(true);

		fieldLayout.addComponents(name, employeeIdTextField, monthField, project);
		fieldLayout.setMargin(true);
		fieldLayout.setSpacing(true);

		return fieldLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
