package org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model;

import java.util.Observable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Insurance;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.InsuranceRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Patient;

import com.vaadin.spring.annotation.ViewScope;

@Component
@ViewScope
public class PatientModel extends Observable {

    @Autowired
    InsuranceRepository insuranceRepository;

    private Patient patient;
    private Insurance insurance;

    public void initialize(Patient patient) {
        this.patient = patient;
        insurance = insuranceRepository.findByPatient(patient);
        setChanged();
        notifyObservers();
    }

    public Patient getPatient() {
        return patient;
    }

    public Insurance getInsurance() {
        return insurance;
    }
}
