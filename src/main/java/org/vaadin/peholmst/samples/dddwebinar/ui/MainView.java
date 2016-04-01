package org.vaadin.peholmst.samples.dddwebinar.ui;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = "")
public class MainView extends VerticalLayout implements View {

    @PostConstruct
    void init() {
        setSpacing(true);
        setMargin(true);

        Label title = new Label("DDD Webinar Sample Application");
        title.addStyleName(ValoTheme.LABEL_H1);
        addComponent(title);

        Label descr = new Label(
            "This application demonstrates one way of turning a domain model into working code. It has some rough edges"
                + " and contains some shortcuts here and there, but it will hopefully give you a hint of how to get started."
                + " Some features that would be must-haves in a production-ready application, such as error handling, are completely missing from this sample.");
        addComponent(descr);

        HorizontalLayout navigationButtons = new HorizontalLayout();
        navigationButtons.setSpacing(true);
        addComponent(navigationButtons);

        navigationButtons
            .addComponent(new Button("Appointments", evt -> getUI().getNavigator().navigateTo("appointments")));
        navigationButtons
            .addComponent(new Button("Procedures", evt -> getUI().getNavigator().navigateTo("admin/procedures")));
        navigationButtons
            .addComponent(new Button("Procedure Categories", evt -> getUI().getNavigator().navigateTo("admin/procedureCategories")));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
