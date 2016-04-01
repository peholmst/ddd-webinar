package org.vaadin.peholmst.samples.dddwebinar.domain.patients;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    Insurance findByPatient(Patient patient);

}
