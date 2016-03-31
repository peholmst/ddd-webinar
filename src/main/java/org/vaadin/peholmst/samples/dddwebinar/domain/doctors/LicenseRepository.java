package org.vaadin.peholmst.samples.dddwebinar.domain.doctors;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface LicenseRepository extends JpaRepository<License, Long> {

    Collection<License> findByDoctor(Doctor doctor);
}
