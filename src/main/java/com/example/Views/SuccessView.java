package com.example.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SuccessView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public SuccessView() {
		VerticalLayout success = success();
		addComponent(success);
		setComponentAlignment(success, Alignment.MIDDLE_CENTER);
		setSizeFull();

	}
   /**
    * Builds the success view
    * @return
    */
	private VerticalLayout success() {
		VerticalLayout thankspage = new VerticalLayout();

		Label title = new Label("<center>Your feedback has been saved successfully<center>");
		Label title1 = new Label("<center>Thank you so much for your feedback<center>");
		title.addStyleName(ValoTheme.LABEL_H2);
		title.addStyleName(ValoTheme.LABEL_BOLD);
		title.setContentMode(ContentMode.HTML);
		title1.setContentMode(ContentMode.HTML);
		thankspage.addComponents(title, title1);
		thankspage.setComponentAlignment(title, Alignment.BOTTOM_CENTER);
		thankspage.setComponentAlignment(title1, Alignment.TOP_CENTER);
		thankspage.setSizeFull();
		return thankspage;

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
