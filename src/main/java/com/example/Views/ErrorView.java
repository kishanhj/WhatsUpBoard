package com.example.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErrorView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public ErrorView() {
		VerticalLayout error = errorPage();
		addComponent(error);
		setComponentAlignment(error, Alignment.MIDDLE_CENTER);
		setSizeFull();
	}

		private VerticalLayout errorPage() {

			VerticalLayout errorPage=new VerticalLayout();
            Image Logo=new Image(null,new ThemeResource("srry.png"));
	  		Label title1=new Label("<center>You have already submitted this form<center>");
	  		title1.setContentMode(ContentMode.HTML);
	  		errorPage.addComponents(Logo,title1);
	  		errorPage.setComponentAlignment(Logo, Alignment.BOTTOM_CENTER);
	  		errorPage.setComponentAlignment(title1, Alignment.TOP_CENTER);
	  		errorPage.setSizeFull();
	  		return errorPage;
	  	}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}



}


