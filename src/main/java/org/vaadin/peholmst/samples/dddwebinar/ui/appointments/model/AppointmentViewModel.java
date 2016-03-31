package org.vaadin.peholmst.samples.dddwebinar.ui.appointments.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.AppointmentRepository;

import com.vaadin.spring.annotation.ViewScope;

@ViewScope
@Component
public class AppointmentViewModel {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    DoctorModel doctorModel;

    @Autowired
    AppointmentModel appointmentModel;

    @Autowired
    PatientModel patientModel;

    private Appointment appointment;

    public void initialize(long appointmentId) {
        appointment = appointmentRepository.findOne(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("No such appointment: " + appointmentId);
        }
        doctorModel.initialize(appointment.getDoctor());
        appointmentModel.initialize(appointment);
        patientModel.initialize(appointment.getPatient());
    }
}
