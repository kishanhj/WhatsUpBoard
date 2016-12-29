package com.example.Views;

import com.example.constants.StringConstants;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Error view for Invalid URL
 *
 * @author kishan.j
 *
 */
public class ErrorView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	public ErrorView() {
		setSizeFull();
		VerticalLayout error = BuildErrorLayout();
		addComponent(error);
		setComponentAlignment(error, Alignment.MIDDLE_CENTER);

	}

    /**
     * Builds the error layout
     * @return VerticalLayout
     */
	private VerticalLayout BuildErrorLayout() {

		VerticalLayout errorLayout = new VerticalLayout();
		Image Logo = new Image(null, new ThemeResource(StringConstants.ERROR_IMG));
		Label title = new Label(StringConstants.ERROR_MSG);
		title.setContentMode(ContentMode.HTML);
		errorLayout.addComponents(Logo, title);
		errorLayout.setComponentAlignment(Logo, Alignment.BOTTOM_CENTER);
		errorLayout.setComponentAlignment(title, Alignment.TOP_CENTER);
		errorLayout.setSizeFull();
		return errorLayout;
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
