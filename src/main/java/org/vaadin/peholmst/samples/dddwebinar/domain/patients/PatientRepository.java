package org.vaadin.peholmst.samples.dddwebinar.domain.patients;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
