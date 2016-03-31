package org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import org.springframework.stereotype.Component;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.Procedure;

import com.vaadin.spring.annotation.ViewScope;

@Component
@ViewScope
public class AppointmentModel extends Observable {

    private Appointment appointment;

    public void initialize(Appointment appointment) {
        this.appointment = appointment;
        setChanged();
        notifyObservers();
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public LocalDateTime getDateTime() {
        return appointment == null ? null : appointment.getDateTime();
    }

    public List<Procedure> getProcedures() {
        return appointment == null ? Collections.emptyList() : appointment.getProcedures();
    }

    public BigDecimal getTotalFee() {
        return appointment == null ? BigDecimal.ZERO : appointment.getTotalFee();
    }
}
