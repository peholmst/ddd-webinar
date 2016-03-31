package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public Payment recordPayment(Receivable receivable, LocalDate date, BigDecimal amount) {
        Payment payment = paymentRepository.saveAndFlush(new Payment(date, amount, receivable));
        return payment;
    }
}
