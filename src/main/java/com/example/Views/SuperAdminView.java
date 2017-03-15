package com.example.Views;

import java.util.List;

import com.example.DAO.AdminDAO;
import com.example.DAO.EmployeeDAO;
import com.example.DAO.ProjectDAO;
import com.example.Helpers.Utils;
import com.example.VO.AdminVO;
import com.example.VO.ProjectVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.example.constants.IntegerConstants;
import com.example.constants.ValidationConstants;
import com.example.validators.EmployeeIdValidator;
import com.example.validators.PasswordValidator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SuperAdminView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "SuperAdminView";

	WhatsUpUI ui;
	Table projectListTable;
	Table adminListTable;

	public SuperAdminView(WhatsUpUI ui) {
		this.ui = ui;
		addComponent(init());

	}

	/**
	 * Initiates to build the layout
	 * @return
	 */
	private Component init() {

		TabSheet functions = new TabSheet();
		functions.addTab(manageAdmins(), "ManageAdmins");
		functions.addTab(manageProjects(), "Manage Projects");
		return functions;

	}

	/**
	 * Builds Mange Admin PAge
	 * @return
	 */
	private Component manageProjects() {
		VerticalLayout mainLayout = new VerticalLayout();

		projectListTable = new Table();
		projectListTable.setWidth("800px");
		projectListTable.setPageLength(8);

		Button addProject = new Button("Add Project");
		addProject.addClickListener(e -> {
			Window window = new Window("Add new Admin");
			window.center();
			window.setResizable(false);
			window.setContent(addProjectLayout(window));
			ui.addWindow(window);

		});

		IndexedContainer container = new IndexedContainer();

		projectListTable.addStyleName("SliderBar");
		projectListTable.setCaption("Project List");

		container.addContainerProperty("Project ID", Integer.class, null);
		container.addContainerProperty("Project Name", String.class, null);

		projectListTable.setContainerDataSource(container);
		projectListTable.setPageLength(Math.min(container.size(), 15));

		loadProjectTable(projectListTable);

		mainLayout.addComponents(addProject, projectListTable);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		mainLayout.setComponentAlignment(addProject, Alignment.MIDDLE_LEFT);
		mainLayout.setComponentAlignment(projectListTable, Alignment.MIDDLE_LEFT);

		return mainLayout;
	}

	/**
	 * Builds Add project Layout
	 * @param window
	 * @return
	 */

	private Component addProjectLayout(Window window) {
		VerticalLayout addProject = new VerticalLayout();
		addProject.setSpacing(true);
		addProject.setMargin(true);

		TextField project =new TextField("Project Name");

		Button ok_button = new Button();
		ok_button.setCaption("OK");
		ok_button.addClickListener(e -> {
			if(project.getValue().equals("") || project.getValue() == null)
			{
				new Notification("ERROR", "Project name cannot be empty",Type.ERROR_MESSAGE).show(ui.getPage());
				return ;
			}
            if(ProjectDAO.addProject(project.getValue()) != IntegerConstants.ZERO)
			Notification.show("Succesfull added");
            else
            	new Notification("ERROR", "Project with this ID alredy exist",Type.ERROR_MESSAGE).show(ui.getPage());
			window.close();
			loadProjectTable(projectListTable);
		});

		Button cancel = new Button();
		cancel.setCaption("Cancel");
		cancel.addClickListener(e -> {
			window.close();
		});

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		buttons.addComponents(ok_button,cancel);
		buttons.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);
		buttons.setComponentAlignment(ok_button, Alignment.BOTTOM_LEFT);

       addProject.addComponents(project,buttons);

		return addProject;

	}

	/**
	 * Loads project table
	 * @param projectListTable
	 */
	private void loadProjectTable(Table projectListTable) {
		List<ProjectVO> projects = ProjectDAO.getAllProjectDetails();
		projectListTable.removeAllItems();
		int i = 1;
		for (ProjectVO project : projects) {
			projectListTable.addItem(new Object[] { project.getProjectId(), project.getProjectName() }, i++);

		}

	}

	/**
	 * Builds Manage Admin Layout
	 * @return
	 */
	private VerticalLayout manageAdmins() {
		VerticalLayout mainLayout = new VerticalLayout();

		adminListTable = new Table();
		adminListTable.setWidth("800px");
		adminListTable.setPageLength(8);

		Button addAdmin = new Button("Add Admin");

		IndexedContainer container = new IndexedContainer();

		adminListTable.addStyleName("SliderBar");
		adminListTable.setCaption("Employee List");

		container.addContainerProperty("Admin ID", String.class, null);
		container.addContainerProperty("Admin Name ", String.class, null);
		container.addContainerProperty("Project", String.class, null);
		container.addContainerProperty("Email Id", String.class, null);

		adminListTable.setContainerDataSource(container);
		adminListTable.setPageLength(Math.min(container.size(), 15));

		adminListTable.setColumnExpandRatio("Employee Name", 40);
		adminListTable.setColumnExpandRatio("Employee Id", 30);
		adminListTable.setColumnExpandRatio("Project", 30);
		adminListTable.setColumnExpandRatio("Email Id", 80);

		loadAdminTable(adminListTable);

		addAdmin.addClickListener(e -> {
			Window window = new Window("Add new Admin");
			window.center();
			window.setResizable(false);
			window.setContent(addAdminLayout(window));
			ui.addWindow(window);
			loadAdminTable(adminListTable);

		});

		mainLayout.addComponents(addAdmin, adminListTable);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		mainLayout.setComponentAlignment(addAdmin, Alignment.MIDDLE_LEFT);
		mainLayout.setComponentAlignment(adminListTable, Alignment.MIDDLE_LEFT);

		return mainLayout;

	}

	/**
	 * Loads admin table
	 * @param adminListTable
	 */
	private void loadAdminTable(Table adminListTable) {
		List<AdminVO> admins = AdminDAO.getAllAdminDetails();
		adminListTable.removeAllItems();
		int i = 1;
		String projectName;
		String adminName;
		for (AdminVO admin : admins) {
			projectName = ProjectDAO.getProjectName(admin.getTProject());
			adminName = EmployeeDAO.getEmployeeName(admin.getAdminId());
			adminListTable.addItem(new Object[] { admin.getAdminId(), adminName, projectName, admin.getAdminEmailId() }, i++);
		}
	}

	/**
	 * Builds Add admin window
	 * @param window
	 * @return
	 */
	public VerticalLayout addAdminLayout(Window window) {
		VerticalLayout super_view = new VerticalLayout();
		super_view.setMargin(true);
		super_view.setSpacing(true);

		TextField adminId = new TextField("Admin Id");
		adminId.addValidator(new EmployeeIdValidator("Please enter a valid ID", adminId));
		adminId.setValidationVisible(false);

		PasswordField password = new PasswordField("Password");
        password.addValidator(new PasswordValidator(password, ValidationConstants.EMPTY_STRING_MSG));
		password.setValidationVisible(false);

		OptionGroup isSuperAdmin = new OptionGroup("Is Super Admin");
		isSuperAdmin.addItem(1);
		isSuperAdmin.setItemCaption(1, "Yes");
		isSuperAdmin.addItem(2);
		isSuperAdmin.setItemCaption(2, "No");
		isSuperAdmin.setValue(2);

		super_view.addComponents(adminId, password, isSuperAdmin);
		super_view.setComponentAlignment(adminId, Alignment.BOTTOM_CENTER);
		super_view.setComponentAlignment(password, Alignment.BOTTOM_CENTER);
		super_view.setComponentAlignment(isSuperAdmin, Alignment.BOTTOM_CENTER);

		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);

		Button ok_button = new Button("OK");
		ok_button.addClickListener(e -> {
			if(!validateAllfields(adminId,password)){
				return ;
			}
			AdminVO admin = getAdminVo(adminId, password, isSuperAdmin);
			if(AdminDAO.addAdmin(admin) != IntegerConstants.ZERO)
			Notification.show("Succesfull added");
			else
			new Notification(ValidationConstants.ERROR, "Admin with this ID alredy exist",Type.ERROR_MESSAGE).show(ui.getPage());
			window.close();
			loadAdminTable(adminListTable);
		});

		buttons.addComponent(ok_button);
		buttons.setComponentAlignment(ok_button, Alignment.BOTTOM_LEFT);

		Button cancel = new Button();
		cancel.setCaption("Cancel");
		cancel.addClickListener(e -> {
			window.close();
		});

		buttons.addComponent(cancel);
		buttons.setComponentAlignment(cancel, Alignment.BOTTOM_RIGHT);

		super_view.addComponent(buttons);
		super_view.setComponentAlignment(buttons, Alignment.BOTTOM_CENTER);

		return super_view;

	}

	private boolean validateAllfields(TextField adminId, PasswordField password) {
		try{
			adminId.validate();
			password.validate();
			return true;
		}catch (InvalidValueException e) {
			adminId.setValidationVisible(true);
			password.setValidationVisible(true);
			Notification.show(ValidationConstants.ERROR, e.getMessage(), Type.ERROR_MESSAGE);
			return false;
		}


	}

	/**
	 * Gets the details of the loggedin admin
	 * @param adminId
	 * @param password
	 * @param isSuperAdmin
	 * @return
	 */
	private AdminVO getAdminVo(TextField adminId, PasswordField password, OptionGroup isSuperAdmin) {
		AdminVO admin = new AdminVO();
		admin.setAdminId(adminId.getValue());
		String encryptedPassword = Utils.encode(password.getValue());
		admin.setPassword(encryptedPassword);
		if (isSuperAdmin.getValue().toString().equals("1"))
			admin.setIsSuperAdmin(true);
		else
			admin.setIsSuperAdmin(false);
		return admin;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
