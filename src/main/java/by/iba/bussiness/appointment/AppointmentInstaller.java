package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
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

    public Appointment installAppointment(Meeting meeting, InvitationTemplate invitationTemplate, Appointment oldAppointment) {
        Appointment newAppointment;
        // FIX ME (vynesti creator naverx i 1 raz vyzyvat')
        if (oldAppointment == null) {
            newAppointment = appointmentCreator.createAppointment(meeting, invitationTemplate);
            appointmentRepository.save(newAppointment);
        } else {
            Appointment updatedAppointment = appointmentHandler.updateAppointment(meeting, invitationTemplate);
            if ((updatedAppointment.getUpdateIndex() == 0 && updatedAppointment.getRescheduleIndex() == 0) ||
                    (updatedAppointment.getRescheduleIndex() > oldAppointment.getRescheduleIndex() ||
                            updatedAppointment.getUpdateIndex() > oldAppointment.getUpdateIndex())) {
                newAppointment = updatedAppointment;
                appointmentRepository.save(newAppointment);
            } else {
                newAppointment = oldAppointment;
            }
        }
        return newAppointment;
    }
}
