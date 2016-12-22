package com.example.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SuccessView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	public SuccessView() {
		addComponent(success());
	}


	      private VerticalLayout success() {

	  		VerticalLayout thankspage=new VerticalLayout();
	  		Image Logo=new Image(null,new ThemeResource("thanks.png"));
	  		Label title=new Label("Your feedback has been saved successfully");
	  		Label title1=new Label("Thanks so much for your feedback");
	  		title.addStyleName(ValoTheme.LABEL_H2);
	  		title.addStyleName(ValoTheme.LABEL_BOLD);

	  		Logo.setHeight("500px");
	  		Logo.setWidth("1000px");

	  		thankspage.setWidth("100%");
	  		thankspage.setHeight("80px");
	  		thankspage.addStyleName("backgroundColor");
	  		thankspage.setSizeFull();

	  		thankspage.addComponents(Logo,title,title1);
	  		thankspage.setExpandRatio(title, 80);
	  		thankspage.setExpandRatio(Logo, 50);
	  		thankspage.setExpandRatio(title1, 80);
	  		thankspage.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
	  		thankspage.setComponentAlignment(title1, Alignment.MIDDLE_CENTER);

	  		return thankspage;
	  	}
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}



}
