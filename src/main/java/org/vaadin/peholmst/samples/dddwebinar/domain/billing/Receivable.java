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
public abstract class Receivable extends AbstractPersistable<Long> implements LedgerEntry {

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

    @Override
    public LocalDate getEntryDate() {
        return getIssueDate();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public BigDecimal getEntryAmount() {
        return amount;
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
