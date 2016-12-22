package com.example.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ErrorView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public ErrorView() {
		addComponent(ErrorPage());
	}

		private VerticalLayout ErrorPage() {

	  		VerticalLayout error_page=new VerticalLayout();
	  		Image Logo=new Image(null,new ThemeResource("error_page.gif"));
	  		Label title=new Label("Please Check Your URL  ");
	  		Label title1=new Label("Try Again");
	  		title.addStyleName(ValoTheme.LABEL_H2);
	  		title.addStyleName(ValoTheme.LABEL_BOLD);

	  		Logo.setHeight("500px");
	  		Logo.setWidth("900px");

	  		error_page.setWidth("100%");
	  		error_page.setHeight("80px");
	  		error_page.addStyleName("backgroundColor");
	  		error_page.setSizeFull();

	  		error_page.addComponents(Logo,title,title1);
	  		error_page.setExpandRatio(title, 80);
	  		error_page.setExpandRatio(Logo, 50);
	  		error_page.setExpandRatio(title1, 80);
	  		error_page.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
	  		error_page.setComponentAlignment(title1, Alignment.MIDDLE_CENTER);

	  		return error_page;
	  	}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}



}


