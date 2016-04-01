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

    @Autowired
    LedgerService ledgerService;

    private Appointment appointment;
    private InsuranceClaim insuranceClaim;
    private Payment insurancePayment;
    private Invoice invoice;
    private Payment invoicePayment;
    private Ledger ledger;

    public void initialize(Appointment appointment) {
        this.appointment = appointment;
        updateLedger();

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

    private void updateLedger() {
        this.ledger = ledgerService.getLedgerForAppointment(appointment);
    }

    public void createInsuranceClaim() {
        insuranceClaim = insuranceClaimService.createInsuranceClaim(appointment);
        updateLedger();
        setChanged();
        notifyObservers();
    }

    public void recordInsuranceClaimPayment(BigDecimal amount) {
        if (insuranceClaim != null && amount != null) {
            insurancePayment = paymentService.recordPayment(insuranceClaim, LocalDate.now(), amount);
            updateLedger();
            setChanged();
            notifyObservers();
        }
    }

    public void createInvoice() {
        invoice = invoiceService.createInvoice(appointment);
        updateLedger();
        setChanged();
        notifyObservers();
    }

    public void recordInvoicePayment(BigDecimal amount) {
        if (invoice != null && amount != null) {
            invoicePayment = paymentService.recordPayment(invoice, LocalDate.now(), amount);
            updateLedger();
            setChanged();
            notifyObservers();
        }
    }

    public Optional<InsuranceClaim> getInsuranceClaim() {
        return Optional.ofNullable(insuranceClaim);
    }

    public Optional<Payment> getInsurancePayment() {
        return Optional.ofNullable(insurancePayment);
    }

    public Optional<Invoice> getInvoice() {
        return Optional.ofNullable(invoice);
    }

    public Optional<Payment> getInvoicePayment() {
        return Optional.ofNullable(invoicePayment);
    }

    public Ledger getLedger() {
        return ledger;
    }
}
