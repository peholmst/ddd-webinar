package org.vaadin.peholmst.samples.dddwebinar.ui.appointments;

import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.Procedure;
import org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model.AppointmentModel;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.LocalDateTimeConverter;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.MoneyConverter;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@ViewScope
@SpringComponent
public class AppointmentSection extends VerticalLayout implements Observer {

    @Autowired
    AppointmentModel model;

    private TextField dateTime;
    private BeanItemContainer<Procedure> procedureContainer;
    private Grid.FooterCell totalFee;

    @PostConstruct
    void init() {
        setSizeFull();
        setSpacing(true);
        Label title = new Label("Appointment");
        title.addStyleName(ValoTheme.LABEL_H2);
        addComponent(title);

        dateTime = new TextField("Date & Time");
        dateTime.setWidth("100%");
        addComponent(dateTime);

        procedureContainer = new BeanItemContainer<>(Procedure.class);

        Grid procedures = new Grid("Procedures", procedureContainer);
        procedures.setColumns("code", "fee");
        procedures.setSizeFull();
        procedures.getColumn("fee").setConverter(new MoneyConverter());
        procedures.setSelectionMode(Grid.SelectionMode.NONE);
        addComponent(procedures);
        setExpandRatio(procedures, 1.0f);
        Grid.FooterRow row = procedures.addFooterRowAt(0);
        row.getCell("code").setText("Total Fee");
        totalFee = row.getCell("fee");

        model.addObserver(this); // Same scope, no need to remove afterwards
    }

    @Override
    public void update(Observable o, Object arg) {
        dateTime.setReadOnly(false);
        dateTime.setValue(
            new LocalDateTimeConverter().convertToPresentation(model.getDateTime(), String.class, getLocale()));
        dateTime.setReadOnly(true);

        procedureContainer.removeAllItems();
        procedureContainer.addAll(model.getProcedures());

        totalFee.setText(new MoneyConverter().convertToPresentation(model.getTotalFee(), String.class, getLocale()));
    }
}
