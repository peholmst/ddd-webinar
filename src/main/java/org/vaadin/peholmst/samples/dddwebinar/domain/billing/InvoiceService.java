package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;

@Service
@Transactional
public class InvoiceService {

    @Autowired
    InsuranceClaimRepository insuranceClaimRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    public Invoice createInvoice(Appointment appointment) {
        BigDecimal deduction = Optional.ofNullable(insuranceClaimRepository.findByAppointment(appointment))
            .map(paymentRepository::findByReceivable).map(Payment::getAmount).orElse(BigDecimal.ZERO);
        BigDecimal amount = appointment.getTotalFee().subtract(deduction);
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Appointment has already been fully paid for");
        }
        Invoice invoice = new Invoice(LocalDate.now(), amount, appointment, LocalDate.now().plusDays(14),
            appointment.getPatient().getFullName());
        return invoiceRepository.saveAndFlush(invoice);
    }
}
