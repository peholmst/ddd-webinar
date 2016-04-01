package org.vaadin.peholmst.samples.dddwebinar.ui.appointments;

import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model.PatientModel;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.PatientConverter;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@ViewScope
@SpringComponent
public class PatientSection extends VerticalLayout implements Observer {

    @Autowired
    PatientModel model;

    @Autowired
    BillingSection billingSection;

    private TextField patientName;
    private TextField insurance;

    @PostConstruct
    void init() {
        setSizeFull();
        setSpacing(true);

        Label title = new Label("Patient");
        title.addStyleName(ValoTheme.LABEL_H2);
        addComponent(title);

        patientName = new TextField("Name");
        patientName.setWidth("100%");
        addComponent(patientName);

        insurance = new TextField("Insurance");
        insurance.setWidth("100%");
        addComponent(insurance);

        addComponent(billingSection);
        setExpandRatio(billingSection, 1.0f);

        model.addObserver(this); // Same scope, no need to remove afterwards
    }

    @Override
    public void update(Observable o, Object arg) {
        patientName.setReadOnly(false);
        patientName
            .setValue(new PatientConverter().convertToPresentation(model.getPatient(), String.class, getLocale()));
        patientName.setReadOnly(true);

        insurance.setReadOnly(false);
        insurance.setValue(String.format("%s - %s", model.getInsurance().getNumber(), model.getInsurance().getInsuranceCompany().getName()));
        insurance.setReadOnly(true);
    }
}
