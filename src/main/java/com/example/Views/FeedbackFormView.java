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
import com.example.constants.StringConstants;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
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

/**
 * Feedback form view which is sent to employees
 *
 * @author kishan.j
 *
 */
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

	/**
	 * TextField which is filled with the name of the employee
	 */
	TextField nameTextField;

	/**
	 * TextField which is filled with the employeeID of the employee
	 */
	TextField employeeIdTextField;

	/**
	 * TextField which is filled with the current month
	 */
	ComboBox monthField;

	/**
	 * TextField which is filled with the project of the employee
	 */
	ComboBox projectField;

	/**
	 * Employee which contains all the details of the employee
	 */
	EmployeeVO employee = new EmployeeVO();

	/**
	 * employeeID of the employee
	 */
	String employeeId;

	/**
	 * current month
	 */
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
	Map<String, Image> qualityImage = new HashMap<String, Image>();

	/**
	 *  Constructor with ui instance as parameter
	 * @param WhatsUpUI ui
	 */
	public FeedbackFormView(WhatsUpUI ui) {
		this.ui = ui;
		qualities = QualityDAO.getAllQualities();
		setSizeUndefined();
		Component feedBackForm = BuildForm();
		addComponents(feedBackForm);
		setComponentAlignment(feedBackForm, Alignment.MIDDLE_CENTER);
	}

	/**
	 *  Constructor
	 * @param ui WhatsUpUI
	 * @param employeeId employeeId obtained from URL
	 * @param month month obtained from URL
	 */
	public FeedbackFormView(WhatsUpUI ui, String employeeId, String month) {
		this.employee = EmployeeDAO.getEmployee(employeeId);
		this.month = month;
		this.employeeId = employeeId;
		this.ui = ui;
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
		headerLayout.addStyleName(StringConstants.STYLE_TITLE_PANEL);
		headerLayout.setSpacing(false);
		headerLayout.setSizeFull();
		headerLayout.setWidth(StringConstants.HUNDRED_PERCENT);
		headerLayout.setHeight(StringConstants.FORTY_PIXEL);
		headerLayout.addComponents(logoBar);
		headerLayout.setExpandRatio(logoBar, 1);

		HorizontalLayout textFields = buildTextFields();
		textFields.addStyleName(StringConstants.STYLE_TEXT);

		VerticalLayout vlayout = new VerticalLayout();
		vlayout.addComponents(headerLayout, textFields);
		vlayout.setComponentAlignment(textFields, Alignment.TOP_CENTER);
		vlayout.setExpandRatio(headerLayout, 1);
		vlayout.setExpandRatio(textFields, 4);


		Table feedBackForm = generateTable();

		final VerticalSplitPanel HeaderPanel = new VerticalSplitPanel();
		HeaderPanel.setSizeFull();
		HeaderPanel.setSplitPosition(20.75f, Unit.PERCENTAGE);
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
		Image brillioLogo = new Image(null, new ThemeResource(StringConstants.IMAGE_B1));

//		Label title = new Label(StringConstants.TITLE_WHATSUP_BOARD);
//		title.setContentMode(ContentMode.HTML);
//		title.addStyleName(ValoTheme.LABEL_H2);
//		title.addStyleName(ValoTheme.LABEL_BOLD);
		Image title = new Image(null, new ThemeResource("logo.png"));

		logoBar.setSizeFull();
		HorizontalLayout buttonsLayout = generateButtons();
		logoBar.addComponents(brillioLogo, title, buttonsLayout);
		 logoBar.setComponentAlignment(title, Alignment.BOTTOM_CENTER);
		logoBar.setExpandRatio(title, 60);
		logoBar.setExpandRatio(brillioLogo, 20);
		logoBar.setExpandRatio(buttonsLayout, 20);
		logoBar.setSpacing(true);
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
					Notification.show(StringConstants.FEEDBACK_SUCCESS);
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
		projectField.validate();
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
		feedbackTable.setPageLength(feedbackTable.size());
		ConfigTable(feedbackTable);
		setImageresources();
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

	/**
	 * Initializes the qualityImages Map
	 */
	 private void setImageresources() {
	 Map<String,Image> Images = new HashMap<String,Image>();

	 Image LeadershipTouch=new Image(null,new ThemeResource("leadership.jpg"));
	 Image Communication=new Image(null,new ThemeResource("communication.jpg"));
	 Image TimelyRecognition=new Image(null,new ThemeResource("timlyrecognition2.jpg"));
	 Image Learning=new Image(null,new ThemeResource("learning.jpg"));
	 Image FeedForward=new Image(null,new ThemeResource("feedforward.jpg"));
	 Image HRResponsiveness=new Image(null,new ThemeResource("HR.jpg"));


	 Images.put("Leadership Touch",LeadershipTouch);
	 Images.put("Communication",Communication);
	 Images.put("Timely Recognition",TimelyRecognition);
	 Images.put("Learning",Learning);
	 Images.put("Feed Forward",FeedForward);
	 Images.put("HR Responsiveness",HRResponsiveness);

	 for(QualityVO quality:qualities){
	 qualityImage.put(quality.getQualityName(), Images.get(quality.getQualityName()));
	 }


	 }

	/**
	 * creates components to be added to the table
	 *
	 * @param feedbackTable
	 * @param qualityName
	 * @param qualityDesc
	 * @param rowNumber
	 */
	private void createFields(Table feedbackTable, String qualityName, String qualityDesc, int rowNumber) {
		Button qualityButton = new Button(StringConstants.SATISFIED);
		TextArea reason = new TextArea();
		qualityDesc = qualityName+":   "+qualityDesc;
		ConfigButtonAndText(qualityButton, reason);
		qualityButtons.put(qualityName, qualityButton);
		qualityReason.put(qualityName, reason);
		feedbackTable.addItem(new Object[] { qualityImage.get(qualityName), qualityDesc, qualityButton, reason }, rowNumber);
	}

	/**
	 * Configures the table button and textarea to required format
	 *
	 * @param button
	 * @param text
	 */
	private void ConfigButtonAndText(Button button, TextArea text) {

		text.setWidth(390, Unit.PIXELS);
		button.setIcon(new ThemeResource(StringConstants.IMAGE_BAD));
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
		feedbackTable.addContainerProperty(StringConstants.AREA, Image.class, null);
		feedbackTable.addContainerProperty(StringConstants.DESCRIPTION, String.class, null);
		feedbackTable.addContainerProperty(StringConstants.SATISFIED, Button.class, null);
		feedbackTable.addContainerProperty(StringConstants.REASON, TextArea.class, null);

		feedbackTable.setColumnWidth(StringConstants.AREA, 200);
		feedbackTable.setColumnWidth(StringConstants.DESCRIPTION, 490);
		feedbackTable.setColumnWidth(StringConstants.SATISFIED, 250);
		feedbackTable.setColumnWidth(StringConstants.REASON, 400);

		feedbackTable.setColumnAlignment(StringConstants.AREA, Align.CENTER);
		feedbackTable.setColumnAlignment(StringConstants.DESCRIPTION, Align.CENTER);
		feedbackTable.setColumnAlignment(StringConstants.SATISFIED, Align.CENTER);
		feedbackTable.setColumnAlignment(StringConstants.REASON, Align.CENTER);

		feedbackTable.setColumnExpandRatio(StringConstants.AREA, 15);
		feedbackTable.setColumnExpandRatio(StringConstants.DESCRIPTION, 20);
		feedbackTable.setColumnExpandRatio(StringConstants.SATISFIED,8);
		feedbackTable.setColumnExpandRatio(StringConstants.REASON, 20);
		feedbackTable.setColumnReorderingAllowed(false);


		feedbackTable.addColumnResizeListener(new ColumnResizeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void columnResize(ColumnResizeEvent event) {

				feedbackTable.setColumnWidth(event.getPropertyId(), event.getPreviousWidth());

			}
		});

		feedbackTable.setColumnHeaders(StringConstants.AREA, StringConstants.DESCRIPTION,StringConstants.FEEDBACK , StringConstants.REASON);
		feedbackTable.addStyleName(ValoTheme.TABLE_COMPACT);
		feedbackTable.addStyleName(Reindeer.TABLE_STRONG);

	}

	/**
	 * Builds the Name,EmployeeID,Month,Project textfields
	 *
	 * @return Horizontal Layout
	 */
	private HorizontalLayout buildTextFields() {
		HorizontalLayout fieldLayout = new HorizontalLayout();
		//fieldLayout.setSpacing(false);

		nameTextField = new TextField(StringConstants.NAME);
		nameTextField.setValue(employee.getEmployeeName());
		nameTextField.setReadOnly(true);

		employeeIdTextField = new TextField(StringConstants.EMPLOYEEID);
		employeeIdTextField.setValue(employee.getEmployeeId());
		employeeIdTextField.setReadOnly(true);

		monthField = new ComboBox(StringConstants.MONTH);
		monthField.setRequired(true);
		monthField.addItem(month);
		monthField.setValue(month);
		monthField.setNullSelectionAllowed(false);
		monthField.setReadOnly(true);

		projectField = new ComboBox(StringConstants.PROJECT);
		projectField.setRequired(true);
		projectField.setNullSelectionAllowed(false);
		String projectName = ProjectDAO.getProjectName(employee.getProjectId());
		projectField.addItem(projectName);
		projectField.setValue(projectName);
		projectField.setReadOnly(true);

		fieldLayout.addComponents(nameTextField, employeeIdTextField, monthField, projectField);
		//fieldLayout.setMargin(true);
		fieldLayout.setSpacing(true);

		return fieldLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
