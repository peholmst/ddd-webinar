package org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Observable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.Doctor;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.License;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.LicenseRepository;

import com.vaadin.spring.annotation.ViewScope;

@Component
@ViewScope
public class DoctorModel extends Observable {

    @Autowired
    LicenseRepository licenseRepository;

    private Doctor doctor;
    private Collection<License> licenses = Collections.emptySet();

    public void initialize(Doctor doctor) {
        this.doctor = doctor;
        licenses = licenseRepository.findByDoctor(doctor);
        setChanged();
        notifyObservers();
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Collection<License> getLicenses() {
        return licenses;
    }
}
