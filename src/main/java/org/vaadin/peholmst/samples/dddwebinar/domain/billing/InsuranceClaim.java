package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.Doctor;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.License;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Insurance;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Patient;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.Procedure;

@Entity
public class InsuranceClaim extends Receivable {

    @ManyToOne
    private License license;

    @ManyToOne
    private Insurance insurance;

    public InsuranceClaim() {
    }

    public InsuranceClaim(LocalDate issueDate, BigDecimal amount, Appointment appointment, License license,
        Insurance insurance) {
        super(issueDate, amount, appointment);
        this.license = license;
        this.insurance = insurance;
    }

    public License getLicense() {
        return license;
    }

    public Insurance getInsurance() {
        return insurance;
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
