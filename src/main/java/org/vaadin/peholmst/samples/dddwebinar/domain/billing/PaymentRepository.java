package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Collection<Payment> findByReceivable(Receivable receivable);
}
