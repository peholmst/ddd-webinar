package org.vaadin.peholmst.samples.dddwebinar.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme(ValoTheme.THEME_NAME)
public class SampleUI extends UI {

    @Autowired
    SpringViewProvider springViewProvider;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setSpacing(true);
        toolbar.setMargin(new MarginInfo(true, true, false, true));

        toolbar.addComponent(new Button("Appointments", evt -> getNavigator().navigateTo("appointments")));
        toolbar.addComponent(new Button("Procedures", evt -> getNavigator().navigateTo("admin/procedures")));
        layout.addComponent(toolbar);

        VerticalLayout viewContainer = new VerticalLayout();
        viewContainer.setSizeFull();
        layout.addComponent(viewContainer);
        layout.setExpandRatio(viewContainer, 1.0f);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(springViewProvider);
        if (navigator.getState().isEmpty()) {
            navigator.navigateTo("appointments");
        }
    }
}
