package org.vaadin.peholmst.samples.dddwebinar.domain.patients;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    Collection<Insurance> findByPatient(Patient patient);

}
