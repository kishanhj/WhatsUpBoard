package com.example.Views;

import java.util.ArrayList;
import java.util.List;

import com.example.DAO.EmployeeDAO;
import com.example.DAO.FeedbackDAO;
import com.example.DAO.LinkCodesDAO;
import com.example.DAO.QualityDAO;
import com.example.DAO.QualityFeedbackDAO;
import com.example.VO.AdminVO;
import com.example.VO.QualityFeedbackVO;
import com.example.WhatsUpApp.WhatsUpUI;
import com.example.constants.IntegerConstants;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
/**
 *Genreates View Feedback View
 */
public class ViewFeedbackView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "ViewFeedbackView";
    WhatsUpUI ui;
    private AdminVO user;

	public ViewFeedbackView(WhatsUpUI ui) {
		this.ui=ui;
		 user=(AdminVO) ui.getSession().getAttribute("user");
		addComponent(init());

	}

	/**
	 * Initialises to build layout
	 * @return
	 */
	private VerticalLayout init() {
		VerticalLayout content = new VerticalLayout();
		ComboBox feedbackMonth = new ComboBox();
		content.setMargin(true);
		content.setSpacing(true);



		feedbackMonth.setCaption("Month");
		List<String> months = FeedbackDAO.getMonthList(user.getTProject());
		for (String month : months)
			feedbackMonth.addItem(month);

		content.addComponent(feedbackMonth);
		content.setComponentAlignment(feedbackMonth, Alignment.MIDDLE_LEFT);

		Table viewFeedbackTable = new Table();

		viewFeedbackTable.setWidth("900px");
		viewFeedbackTable.setPageLength(15);

		IndexedContainer feedbackContainer = new IndexedContainer();


		ProgressBar completedFeedbacks = new ProgressBar(viewFeedbackTable.size());
		completedFeedbacks.setWidth("900px");

		completedFeedbacks.addContextClickListener(e -> {
			float count = completedFeedbacks.getValue() * 100;
			int employeesLeft = (int) (EmployeeDAO.getEmployeeCount(user.getTProject())
					* (1 - completedFeedbacks.getValue()));
			Notification.show(count + "% completed,Number of employees left: " + employeesLeft);
		});

		viewFeedbackTable.addStyleName("SliderBar");
		viewFeedbackTable.setSelectable(true);

		feedbackMonth.addValueChangeListener(e -> {
			viewFeedbackTable.removeAllItems();
			showFeedbacks(viewFeedbackTable, feedbackMonth);
			viewFeedbackTable.setPageLength(Math.min(feedbackContainer.size(), 10));
			int employeeCount = EmployeeDAO.getEmployeeCount(user.getTProject());
			completedFeedbacks.setValue((float) feedbackContainer.size()/(employeeCount * 6));
			if(completedFeedbacks.getValue() == IntegerConstants.ONE)
				LinkCodesDAO.deleteCode(feedbackMonth);
		});

		feedbackContainer.addContainerProperty("Employee Name", String.class, null);
		feedbackContainer.addContainerProperty("Quality Name", String.class, null);
		feedbackContainer.addContainerProperty("Satisfied or Not Satisfied", String.class, null);
		feedbackContainer.addContainerProperty("Comments", String.class, null);

		viewFeedbackTable.setColumnExpandRatio("Employee Name", 40);
		viewFeedbackTable.setColumnExpandRatio("Quality Name", 40);
		viewFeedbackTable.setColumnExpandRatio("Satisfied or Not Satisfied", 50);
		viewFeedbackTable.setColumnExpandRatio("Comments", 80);

		viewFeedbackTable.setPageLength(Math.min(feedbackContainer.size(), 10));

		viewFeedbackTable.setContainerDataSource(feedbackContainer);


		content.addComponents(viewFeedbackTable,completedFeedbacks);
		return content;

	}


	/**
	 * Displays values in the table
	 * @param view_feedback
	 * @param feedback_month
	 */
	private void showFeedbacks(Table view_feedback, ComboBox feedback_month) {
		List<QualityFeedbackVO> feedbacks = QualityFeedbackDAO.getMonthWiseFeedbacks(feedback_month,user.getTProject());
		String employeeName;
		String qualityName;
		String feedbackType;
		int i = 1;
		for (QualityFeedbackVO feedback : feedbacks) {
			//if(user.getTProject() == feedback.getFeedbackId()){
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

		}//}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
