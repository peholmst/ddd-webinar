package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Receivable extends AbstractPersistable<Long> {

    @Temporal(TemporalType.DATE)
    private Date issueDate;
    private BigDecimal amount;
    @ManyToOne(optional = false)
    private Appointment appointment;

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
