package org.vaadin.peholmst.samples.dddwebinar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.AppointmentRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.licensing.LicenseType;
import org.vaadin.peholmst.samples.dddwebinar.domain.licensing.LicenseTypeRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.InsuranceRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.Doctor;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.DoctorRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.License;
import org.vaadin.peholmst.samples.dddwebinar.domain.doctors.LicenseRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.insurance.InsuranceCompany;
import org.vaadin.peholmst.samples.dddwebinar.domain.insurance.InsuranceCompanyRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Insurance;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.Patient;
import org.vaadin.peholmst.samples.dddwebinar.domain.patients.PatientRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.Procedure;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.ProcedureCategory;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.ProcedureCategoryRepository;
import org.vaadin.peholmst.samples.dddwebinar.domain.procedures.ProcedureRepository;

@Component
class TestDataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataGenerator.class);

    private static final Random RND = new Random();

    @Autowired
    LicenseTypeRepository licenseTypeRepository;

    @Autowired
    InsuranceCompanyRepository insuranceCompanyRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    InsuranceRepository insuranceRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    LicenseRepository licenseRepository;

    @Autowired
    ProcedureCategoryRepository procedureCategoryRepository;

    @Autowired
    ProcedureRepository procedureRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @PostConstruct
    @Transactional
    void init() {
        LOGGER.info("Saving LicenseTypes");
        LicenseType generalPractice = licenseTypeRepository.save(new LicenseType("General Practice", "1223G0001X"));
        LicenseType orthodontics = licenseTypeRepository.save(new LicenseType("Orthodontics", "1223X0400X"));
        LicenseType pediatricDentistry = licenseTypeRepository
            .save(new LicenseType("Pediatric Dentistry", "1223P0221X"));
        LicenseType periodontics = licenseTypeRepository.save(new LicenseType("Periodontics", "1223P0300X"));
        LicenseType prosthodontics = licenseTypeRepository.save(new LicenseType("Prosthodontics", "1223P0700X"));
        LicenseType oralSurgery = licenseTypeRepository
            .save(new LicenseType("Oral and Maxillofacial Surgery", "1223S0112X"));
        licenseTypeRepository.flush();

        LOGGER.info("Saving ProcedureCategories");
        ProcedureCategory generalCategory = procedureCategoryRepository
            .save(new ProcedureCategory.Builder().withName("General").withLicenseType(generalPractice, 1).build());
        ProcedureCategory orthodonticCategory = procedureCategoryRepository
            .save(new ProcedureCategory.Builder().withName("Orthodontic").withLicenseType(orthodontics, 1)
                .withLicenseType(pediatricDentistry, 2).withLicenseType(generalPractice, 3).build());
        ProcedureCategory periodonticCategory = procedureCategoryRepository
            .save(new ProcedureCategory.Builder().withName("Periodontic").withLicenseType(periodontics, 1)
                .withLicenseType(orthodontics, 2).withLicenseType(generalPractice, 3).build());
        ProcedureCategory prosthodonticCategory = procedureCategoryRepository
            .save(new ProcedureCategory.Builder().withName("Prosthodontics").withLicenseType(prosthodontics, 1)
                .withLicenseType(orthodontics, 2).withLicenseType(generalPractice, 3).build());
        ProcedureCategory oralSurgeryCategory = procedureCategoryRepository
            .save(new ProcedureCategory.Builder().withName("Oral Surgery").withLicenseType(oralSurgery, 1).build());
        procedureCategoryRepository.flush();

        LOGGER.info("Saving InsuranceCompanies");
        final InsuranceCompany acmeHealth = insuranceCompanyRepository
            .save(new InsuranceCompany("Acme Health", "Acme Road 123"));
        final InsuranceCompany globexInsurance = insuranceCompanyRepository
            .save(new InsuranceCompany("Globex Insurance", "Globex Avenue 456"));
        insuranceCompanyRepository.flush();

        LOGGER.info("Saving Patients and Insurances");
        Set<Patient> patients = new HashSet<>();
        for (int i = 0; i < 10; ++i) {
            Patient patient = patientRepository.save(new Patient(FIRST_NAMES[RND.nextInt(FIRST_NAMES.length)],
                LAST_NAMES[RND.nextInt(LAST_NAMES.length)], randomDate(40)));
            patients.add(patient);

            if (RND.nextBoolean()) {
                insuranceRepository.save(new Insurance(acmeHealth, patient, insuranceNumber()));
            } else {
                insuranceRepository.save(new Insurance(globexInsurance, patient, insuranceNumber()));
            }
        }
        patientRepository.flush();
        insuranceRepository.flush();

        LOGGER.info("Saving Doctors and Licenses");
        Doctor drArnold = doctorRepository.save(new Doctor("Arnold", "Lawrence"));
        licenseRepository.save(new License(generalPractice, drArnold, licenseNumber()));
        licenseRepository.save(new License(orthodontics, drArnold, licenseNumber()));
        licenseRepository.save(new License(pediatricDentistry, drArnold, licenseNumber()));

        Doctor drAnne = doctorRepository.save(new Doctor("Anne", "Knife"));
        licenseRepository.save(new License(oralSurgery, drAnne, licenseNumber()));
        licenseRepository.save(new License(generalPractice, drAnne, licenseNumber()));

        Doctor drPeter = doctorRepository.save(new Doctor("Peter", "Parker"));
        licenseRepository.save(new License(periodontics, drPeter, licenseNumber()));
        licenseRepository.save(new License(orthodontics, drPeter, licenseNumber()));
        licenseRepository.save(new License(generalPractice, drPeter, licenseNumber()));

        Doctor drClark = doctorRepository.save(new Doctor("Clark", "Kent"));
        licenseRepository.save(new License(prosthodontics, drClark, licenseNumber()));
        licenseRepository.save(new License(periodontics, drClark, licenseNumber()));
        licenseRepository.save(new License(generalPractice, drClark, licenseNumber()));

        doctorRepository.flush();
        licenseRepository.flush();

        LOGGER.info("Saving Procedures");
        int code = 1;
        Set<Procedure> generalProcecures = new HashSet<>();
        Set<Procedure> orthodonticProcedures = new HashSet<>();
        Set<Procedure> periodonticProcedures = new HashSet<>();
        Set<Procedure> prosthodonticProcedures = new HashSet<>();
        Set<Procedure> oralSurgeryProcedures = new HashSet<>();
        for (int i = 0; i < 50; ++i) {
            generalProcecures
                .add(procedureRepository.save(new Procedure(fee(), String.format("D%04d", code++), generalCategory)));
            orthodonticProcedures.add(
                procedureRepository.save(new Procedure(fee(), String.format("D%04d", code++), orthodonticCategory)));
            periodonticProcedures.add(
                procedureRepository.save(new Procedure(fee(), String.format("D%04d", code++), periodonticCategory)));
            prosthodonticProcedures.add(
                procedureRepository.save(new Procedure(fee(), String.format("D%04d", code++), prosthodonticCategory)));
            oralSurgeryProcedures.add(
                procedureRepository.save(new Procedure(fee(), String.format("D%04d", code++), oralSurgeryCategory)));
        }
        procedureRepository.flush();

        LOGGER.info("Saving Appointments");
        for (int i = 0; i < 20; ++i) {
            appointmentRepository.save(new Appointment.Builder().withDate(randomDate(2)).withTime(randomTime())
                .withProcedures(pickRandom(generalProcecures, 5)).withDoctor(drArnold).withPatient(pickRandom(patients))
                .build());
            appointmentRepository.save(new Appointment.Builder().withDate(randomDate(2)).withTime(randomTime())
                .withProcedures(pickRandom(orthodonticProcedures, 5)).withDoctor(drPeter).withPatient(pickRandom(patients))
                .build());
            appointmentRepository.save(new Appointment.Builder().withDate(randomDate(2)).withTime(randomTime())
                .withProcedures(pickRandom(periodonticProcedures, 5)).withDoctor(drClark).withPatient(pickRandom(patients))
                .build());
            appointmentRepository.save(new Appointment.Builder().withDate(randomDate(2)).withTime(randomTime())
                .withProcedures(pickRandom(prosthodonticProcedures, 5)).withDoctor(drClark).withPatient(pickRandom(patients))
                .build());
            appointmentRepository.save(new Appointment.Builder().withDate(randomDate(2)).withTime(randomTime())
                .withProcedures(pickRandom(oralSurgeryProcedures, 5)).withDoctor(drAnne).withPatient(pickRandom(patients))
                .build());
        }
        appointmentRepository.flush();
    }

    private static final String[] FIRST_NAMES = { "Alice", "Bob", "Eve", "John", "Joe", "Kate", "Martha" };
    private static final String[] LAST_NAMES = { "Smith", "Doe", "Stewart", "McPeterson", "Cool" };

    private static LocalDate randomDate(int yearsBack) {
        LocalDate now = LocalDate.now();
        return LocalDate.of(now.getYear() - RND.nextInt(yearsBack), RND.nextInt(12) + 1, RND.nextInt(28) + 1);
    }

    private static LocalTime randomTime() {
        return LocalTime.of(8 + RND.nextInt(10), 5 * RND.nextInt(12));
    }

    private static final AtomicInteger runningInsuranceNumber = new AtomicInteger();

    private static String insuranceNumber() {
        return String.format("%03d-%03d-%03d", RND.nextInt(1000), RND.nextInt(1000),
            runningInsuranceNumber.incrementAndGet());
    }

    private static String licenseNumber() {
        return String.format("%08d", RND.nextInt(1000000000));
    }

    private static <T> Set<T> pickRandom(Set<T> source, int count) {
        List<T> src = new ArrayList<>(source);
        Set<T> dest = new HashSet<>();
        for (int i = 0; i < count; ++i) {
            dest.add(src.remove(RND.nextInt(src.size())));
        }
        return dest;
    }

    private static <T> T pickRandom(Set<T> source) {
        return pickRandom(source, 1).iterator().next();
    }

    private static BigDecimal fee() {
        return new BigDecimal(50 + RND.nextInt(10) * 5);
    }

}
