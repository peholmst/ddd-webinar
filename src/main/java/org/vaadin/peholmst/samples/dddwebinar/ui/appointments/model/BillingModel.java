package org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;
import org.vaadin.peholmst.samples.dddwebinar.domain.billing.*;

import com.vaadin.spring.annotation.ViewScope;

@ViewScope
@Component
public class BillingModel extends Observable {

    @Autowired
    InsuranceClaimRepository insuranceClaimRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    InsuranceClaimService insuranceClaimService;

    @Autowired
    InvoiceService invoiceService;

    private Appointment appointment;
    private InsuranceClaim insuranceClaim;
    private Payment insurancePayment;
    private Invoice invoice;
    private Payment invoicePayment;

    public void initialize(Appointment appointment) {
        this.appointment = appointment;
        insuranceClaim = insuranceClaimRepository.findByAppointment(appointment);
        if (insuranceClaim != null) {
            insurancePayment = paymentRepository.findByReceivable(insuranceClaim);
        }
        invoice = invoiceRepository.findByAppointment(appointment);
        if (invoice != null) {
            invoicePayment = paymentRepository.findByReceivable(invoice);
        }
        setChanged();
        notifyObservers();
    }

    public void createInsuranceClaim() {
        insuranceClaim = insuranceClaimService.createInsuranceClaim(appointment);
        setChanged();
        notifyObservers();
    }

    public void recordInsuranceClaimPayment(BigDecimal amount) {
        if (insuranceClaim != null && amount != null) {
            insurancePayment = paymentService.recordPayment(insuranceClaim, LocalDate.now(), amount);
            setChanged();
            notifyObservers();
        }
    }

    public void createInvoice() {
        invoice = invoiceService.createInvoice(appointment);
        setChanged();
        notifyObservers();
    }

    public void recordInvoicePayment(BigDecimal amount) {
        if (invoice != null && amount != null) {
            invoicePayment = paymentService.recordPayment(invoice, LocalDate.now(), amount);
            setChanged();
            notifyObservers();
        }
    }

    public Optional<BigDecimal> getInsuranceClaimAmount() {
        return insuranceClaim == null ? Optional.empty() : Optional.of(insuranceClaim.getAmount());
    }

    public Optional<BigDecimal> getInsurancePaymentAmount() {
        return insurancePayment == null ? Optional.empty() : Optional.of(insurancePayment.getAmount());
    }

    public Optional<BigDecimal> getInvoiceAmount() {
        return invoice == null ? Optional.empty() : Optional.of(invoice.getAmount());
    }

    public Optional<BigDecimal> getInvoicePaymentAmount() {
        return invoicePayment == null ? Optional.empty() : Optional.of(invoicePayment.getAmount());
    }

    public BigDecimal getOutstanding() {
        if (appointment == null) {
            return BigDecimal.ZERO;
        } else {
            BigDecimal outstanding = appointment.getTotalFee();
            if (insurancePayment != null) {
                outstanding = outstanding.subtract(insurancePayment.getAmount());
            }
            if (invoicePayment != null) {
                outstanding = outstanding.subtract(invoicePayment.getAmount());
            }
            return outstanding;
        }
    }
}
