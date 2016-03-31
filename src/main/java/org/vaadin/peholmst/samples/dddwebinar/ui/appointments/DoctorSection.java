package org.vaadin.peholmst.samples.dddwebinar.ui.appointments;

import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.License;
import org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model.DoctorModel;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.DoctorConverter;

@ViewScope
@SpringComponent
public class DoctorSection extends VerticalLayout implements Observer {

    @Autowired
    DoctorModel model;

    private TextField doctorName;
    private BeanItemContainer<License> licenseContainer;

    @PostConstruct
    void init() {
        setSizeFull();
        setSpacing(true);
        Label title = new Label("Doctor");
        title.addStyleName(ValoTheme.LABEL_H2);
        addComponent(title);

        doctorName = new TextField("Name");
        doctorName.setWidth("100%");
        addComponent(doctorName);

        licenseContainer = new BeanItemContainer<>(License.class);
        licenseContainer.addNestedContainerProperty("type.name");

        Grid doctorLicenses = new Grid("Licenses", licenseContainer);
        doctorLicenses.setColumns("number", "type.name");
        doctorLicenses.setSizeFull();
        addComponent(doctorLicenses);
        setExpandRatio(doctorLicenses, 1.0f);

        model.addObserver(this); // Same scope, no need to remove afterwards
    }

    @Override
    public void update(Observable o, Object arg) {
        doctorName.setReadOnly(false);
        doctorName.setValue(new DoctorConverter().convertToPresentation(model.getDoctor(), String.class, getLocale()));
        doctorName.setReadOnly(true);

        licenseContainer.removeAllItems();
        licenseContainer.addAll(model.getLicenses());
    }
}
