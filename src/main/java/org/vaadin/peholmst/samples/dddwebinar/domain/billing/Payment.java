package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Payment extends AbstractPersistable<Long> implements LedgerEntry {

    private Date paymentDate;
    private BigDecimal amount;
    @OneToOne
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

    @Override
    public LocalDate getEntryDate() {
        return getPaymentDate();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public BigDecimal getEntryAmount() {
        return amount.negate();
    }

    @Override
    public String getEntryDescription() {
        return String.format("Payment for %s", receivable.getEntryDescription());
    }

    public Receivable getReceivable() {
        return receivable;
    }
}
