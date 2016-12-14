package com.example.Views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class DashBoardView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	Label l=new Label("This is the DashBoard");

	public DashBoardView() {
		addComponent(l);
	}

	@Override
	public void enter(ViewChangeEvent event) {


	}

}
