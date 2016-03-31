package org.vaadin.peholmst.samples.dddwebinar.ui.appointments;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.Appointment;
import org.vaadin.peholmst.samples.dddwebinar.domain.appointments.AppointmentRepository;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.DoctorConverter;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.LocalDateTimeConverter;
import org.vaadin.peholmst.samples.dddwebinar.ui.converters.PatientConverter;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = "appointments")
public class AppointmentListView extends VerticalLayout implements View {

    @Autowired
    AppointmentRepository appointmentRepository;

    private BeanItemContainer<Appointment> appointmentContainer;
    private Grid appointmentGrid;
    private Button openAppointment;

    @PostConstruct
    void init() {
        setMargin(true);
        setSpacing(true);
        setSizeFull();
        Label label = new Label("Appointments");
        label.addStyleName(ValoTheme.LABEL_H1);
        addComponent(label);

        appointmentContainer = new BeanItemContainer<>(Appointment.class);
        appointmentGrid = new Grid(appointmentContainer);
        appointmentGrid.setColumns("patient", "doctor", "dateTime");
        appointmentGrid.getColumn("patient").setConverter(new PatientConverter());
        appointmentGrid.getColumn("doctor").setConverter(new DoctorConverter());
        appointmentGrid.getColumn("dateTime").setHeaderCaption("Date & Time")
            .setConverter(new LocalDateTimeConverter());
        appointmentGrid.addSelectionListener(event -> toggleOpenAppointmentEnablement(event.getSelected().size() > 0));
        appointmentGrid.setSizeFull();

        addComponent(appointmentGrid);
        setExpandRatio(appointmentGrid, 1.0f);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setWidth("100%");
        addComponent(buttons);

        Button refresh = new Button("Refresh", this::refresh);
        buttons.addComponent(refresh);

        openAppointment = new Button("Open Appointment", this::openAppointment);
        openAppointment.addStyleName(ValoTheme.BUTTON_PRIMARY);
        openAppointment.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        buttons.addComponent(openAppointment);
        buttons.setComponentAlignment(openAppointment, Alignment.MIDDLE_RIGHT);
        toggleOpenAppointmentEnablement(false);

        refresh(null);
    }

    private void refresh(Button.ClickEvent event) {
        appointmentContainer.removeAllItems();
        appointmentContainer.addAll(appointmentRepository.findAllByOrderByDateDescTimeAsc());
    }

    private void openAppointment(Button.ClickEvent event) {
        Appointment appointment = (Appointment) appointmentGrid.getSelectedRow();
        if (appointment != null) {
            getUI().getNavigator().navigateTo(String.format("appointment/%d", appointment.getId()));
        }
    }

    private void toggleOpenAppointmentEnablement(boolean enabled) {
        openAppointment.setEnabled(enabled);
        if (enabled) {
            openAppointment.setDescription("");
        } else {
            openAppointment.setDescription("Please select an appointment from the list");
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
