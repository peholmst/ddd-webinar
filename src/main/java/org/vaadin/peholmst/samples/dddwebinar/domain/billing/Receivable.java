package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Receivable extends AbstractPersistable<Long> {

    private Date issueDate;
    private BigDecimal amount;
    @ManyToOne(optional = false)
    private Appointment appointment;

    public Receivable() {
    }

    public Receivable(LocalDate issueDate, BigDecimal amount, Appointment appointment) {
        this.issueDate = Date.valueOf(issueDate);
        this.amount = amount;
        this.appointment = appointment;
    }

    public LocalDate getIssueDate() {
        return issueDate == null ? null : issueDate.toLocalDate();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
