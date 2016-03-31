package org.vaadin.peholmst.samples.dddwebinar.domain.appointments;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.Doctor;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Patient;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.Procedure;

@Entity
public class Appointment extends AbstractPersistable<Long> {

    @ManyToOne
    private Patient patient;
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderColumn
    private List<Procedure> procedures = new ArrayList<>();
    @ManyToOne
    private Doctor doctor;
    private Date date;
    private Time time;

    public Appointment() {
    }

    public Appointment(Patient patient,
        List<Procedure> procedures, Doctor doctor, LocalDate date, LocalTime time) {
        this.patient = patient;
        this.procedures = procedures;
        this.doctor = doctor;
        this.date = Date.valueOf(date);
        this.time = Time.valueOf(time);
    }

    public Patient getPatient() {
        return patient;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public BigDecimal getTotalFee() {
        BigDecimal total = BigDecimal.ZERO;
        for (Procedure p : procedures) {
            total = total.add(p.getFee());
        }
        return total;
    }

    public LocalDate getDate() {
        return date == null ? null : date.toLocalDate();
    }

    public LocalTime getTime() {
        return time == null ? null : time.toLocalTime();
    }

    public LocalDateTime getDateTime() {
        return getDate().atTime(getTime());
    }

    public static class Builder {
        private Patient patient;
        private List<Procedure> procedures = new ArrayList<>();
        private Doctor doctor;
        private LocalDate date;
        private LocalTime time;

        public Builder withPatient(Patient patient) {
            this.patient = patient;
            return this;
        }

        public Builder withProcedure(Procedure procedure) {
            this.procedures.add(procedure);
            return this;
        }

        public Builder withProcedures(Collection<Procedure> procedures) {
            this.procedures.addAll(procedures);
            return this;
        }

        public Builder withDoctor(Doctor doctor) {
            this.doctor = doctor;
            return this;
        }

        public Builder withDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder withTime(LocalTime time) {
            this.time = time;
            return this;
        }

        public Appointment build() {
            return new Appointment(patient, procedures, doctor, date, time);
        }
    }
}
