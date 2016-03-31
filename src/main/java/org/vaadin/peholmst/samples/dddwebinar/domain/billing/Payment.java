package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Payment extends AbstractPersistable<Long> {

    private Date paymentDate;
    private BigDecimal amount;
    @ManyToOne
    private Receivable receivable;

    public Payment() {
    }

    public Payment(LocalDate paymentDate, BigDecimal amount, Receivable receivable) {
        this.paymentDate = Date.valueOf(paymentDate);
        this.amount = amount;
        this.receivable = receivable;
    }

    public LocalDate getPaymentDate() {
        return paymentDate == null ? null : paymentDate.toLocalDate();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Receivable getReceivable() {
        return receivable;
    }
}
