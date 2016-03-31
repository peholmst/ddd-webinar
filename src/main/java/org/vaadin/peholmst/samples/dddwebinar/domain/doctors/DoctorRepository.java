package org.vaadin.peholmst.samples.dddwebinar.domain.doctors;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
