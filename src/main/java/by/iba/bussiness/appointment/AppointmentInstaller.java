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

    public Appointment installAppointment(Meeting meeting, InvitationTemplate invitationTemplate) {
        Appointment appointment;
        BigInteger meetingId = meeting.getId();
        Appointment oldAppointment = appointmentRepository.getByMeetingId(meetingId);
        if (oldAppointment == null) {
            appointment = appointmentCreator.createAppointment(meeting, invitationTemplate);
            appointmentRepository.save(appointment);
        } else {
            Appointment updatedAppointment = appointmentHandler.getUpdatedAppointment(meeting, invitationTemplate);
            if ((updatedAppointment.getUpdateIndex() == 0 && updatedAppointment.getRescheduleIndex() == 0) ||
                    (updatedAppointment.getRescheduleIndex() > oldAppointment.getRescheduleIndex() ||
                            updatedAppointment.getUpdateIndex() > oldAppointment.getUpdateIndex())) {
                appointment = updatedAppointment;
                appointmentRepository.save(appointment);
            } else {
                appointment = oldAppointment;
            }
        }
        return appointment;
    }
}
