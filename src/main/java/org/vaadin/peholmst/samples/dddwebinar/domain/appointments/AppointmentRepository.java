package org.vaadin.peholmst.samples.dddwebinar.domain.appointments;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByOrderByDateDescTimeAsc();
}
