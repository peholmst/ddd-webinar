package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.Doctor;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.License;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Insurance;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Patient;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.Procedure;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class InsuranceClaim extends Receivable {

    @ManyToOne
    private License license;

    @ManyToOne
    private Insurance insurance;

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public Patient getPatient() {
        return getAppointment().getPatient();
    }

    public Doctor getDoctor() {
        return getAppointment().getDoctor();
    }

    public List<Procedure> getProcedures() {
        return getAppointment().getProcedures();
    }
}
