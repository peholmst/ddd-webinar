package org.vaadin.peholmst.samples.dddwebinar.domain.billing;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.License;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.LicenseService;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Insurance;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.InsuranceRepository;

@Service
@Transactional
public class InsuranceClaimService {

    @Autowired
    LicenseService licenseService;

    @Autowired
    InsuranceClaimRepository insuranceClaimRepository;

    @Autowired
    InsuranceRepository insuranceRepository;

    public InsuranceClaim createInsuranceClaim(Appointment appointment) {
        if (insuranceClaimRepository.findByAppointment(appointment) != null) {
            throw new IllegalArgumentException("The specified appointment already has an insurance claim");
        }

        License license = licenseService.selectBestLicense(appointment.getProcedures(), appointment.getDoctor())
            .orElseThrow(
                () -> new IllegalArgumentException("The doctor is not licensed to perform all the procedures"));

        Insurance insurance = insuranceRepository.findByPatient(appointment.getPatient());
        if (insurance == null) {
            throw new IllegalArgumentException("The patient has no insurance");
        }

        InsuranceClaim claim = new InsuranceClaim(LocalDate.now(), appointment.getTotalFee(), appointment, license,
            insurance);
        return insuranceClaimRepository.saveAndFlush(claim);
    }
}
