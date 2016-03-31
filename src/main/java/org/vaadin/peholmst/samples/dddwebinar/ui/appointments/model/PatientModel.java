package org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model;

import com.vaadin.spring.annotation.ViewScope;
import org.springframework.stereotype.Component;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Insurance;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Patient;

import java.util.Collection;
import java.util.Observable;

@Component
@ViewScope
public class PatientModel extends Observable {

    private Patient patient;
    private Collection<Insurance> insurances;

    public void initialize(Patient patient) {
        this.patient = patient;
        setChanged();
        notifyObservers();
    }

    public Patient getPatient() {
        return patient;
    }
}
