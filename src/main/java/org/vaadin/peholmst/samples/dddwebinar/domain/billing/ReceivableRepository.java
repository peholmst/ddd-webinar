package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;

@NoRepositoryBean
public interface ReceivableRepository<E extends Receivable> extends JpaRepository<E, Long> {

    E findByAppointment(Appointment appointment);
}
