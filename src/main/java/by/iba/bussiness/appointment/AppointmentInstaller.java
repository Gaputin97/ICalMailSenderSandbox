package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.handler.AppointmentHandler;
import by.iba.bussiness.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentInstaller {
    private AppointmentRepository appointmentRepository;
    private AppointmentHandler appointmentHandler;
    private AppointmentCreator appointmentCreator;

    @Autowired
    public AppointmentInstaller(AppointmentRepository appointmentRepository,
                                AppointmentHandler appointmentHandler,
                                AppointmentCreator appointmentCreator) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentHandler = appointmentHandler;
        this.appointmentCreator = appointmentCreator;
    }

    public Appointment installAppointment(Appointment newAppointment, Appointment currentAppointment) {
        Appointment updatedAppointment = appointmentHandler.updateAppointmentIndex(newAppointment, currentAppointment);
        if ((updatedAppointment.getUpdateIndex() == 0 && updatedAppointment.getRescheduleIndex() == 0) ||
                (updatedAppointment.getRescheduleIndex() > currentAppointment.getRescheduleIndex() ||
                        updatedAppointment.getUpdateIndex() > currentAppointment.getUpdateIndex())) {
            newAppointment = updatedAppointment;
            appointmentRepository.save(newAppointment);
        } else {
            newAppointment = currentAppointment;
        }
        return newAppointment;
    }
}

