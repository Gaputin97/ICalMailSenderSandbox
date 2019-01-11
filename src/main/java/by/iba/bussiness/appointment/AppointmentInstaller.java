package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class AppointmentInstaller {

    private AppointmentRepository appointmentRepository;
    private AppointmentCreator appointmentCreator;
    private AppointmentHandler appointmentHandler;

    @Autowired
    public AppointmentInstaller(AppointmentRepository appointmentRepository,
                                AppointmentCreator appointmentCreator,
                                AppointmentHandler appointmentHandler) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentCreator = appointmentCreator;
        this.appointmentHandler = appointmentHandler;
    }

    public Appointment installAppointment(Meeting meeting, InvitationTemplate invitationTemplate, Appointment oldAppointment) {
        Appointment newAppointment;
        Appointment updatedAppointment = appointmentHandler.getUpdatedAppointment(meeting, invitationTemplate);
        if ((updatedAppointment.getUpdateIndex() == 0 && updatedAppointment.getRescheduleIndex() == 0) ||
                (updatedAppointment.getRescheduleIndex() > oldAppointment.getRescheduleIndex() ||
                        updatedAppointment.getUpdateIndex() > oldAppointment.getUpdateIndex())) {
            newAppointment = updatedAppointment;
            appointmentRepository.save(newAppointment);
        } else {
            newAppointment = oldAppointment;
        }

        return newAppointment;
    }
}
