package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class AppointmentHandler {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentHandler.class);
    private AppointmentRepository appointmentRepository;
    private AppointmentCreator appointmentCreator;

    @Autowired
    public AppointmentHandler(AppointmentRepository appointmentRepository, AppointmentCreator appointmentCreator) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentCreator = appointmentCreator;
    }

    public Appointment updateAppointment(Meeting updatedMeeting, InvitationTemplate updatedTemplate) {
        Appointment newAppointment = appointmentCreator.createAppointment(updatedMeeting, updatedTemplate);
        Appointment updatedAppointment = appointmentRepository.getByMeetingId(updatedMeeting.getId());

        BigInteger updatedAppId = updatedAppointment.getId();
        int updatedAppUpdatedIndex = updatedAppointment.getUpdateIndex();
        int updatedAppRescheduleIndex = updatedAppointment.getRescheduleIndex();

        if (updatedAppointment.equals(newAppointment)) {
            newAppointment = updatedAppointment;
        } else {
            int maximumIndex = getMaximumIndex(updatedAppointment);
            List<Session> updatedAppSessions = updatedAppointment.getSessionList();
            List<Session> newAppSessions = newAppointment.getSessionList();
            if (!updatedAppSessions.equals(newAppSessions)) {
                newAppointment.setUpdateIndex(updatedAppUpdatedIndex);
                newAppointment.setRescheduleIndex(++maximumIndex);
            } else {
                newAppointment.setRescheduleIndex(updatedAppRescheduleIndex);
                newAppointment.setUpdateIndex(++maximumIndex);
            }
        }
        newAppointment.setId(updatedAppId);
        return newAppointment;
    }

    public int getMaximumIndex(Appointment sourceAppointment) {
        int updateIndex = sourceAppointment.getUpdateIndex();
        int rescheduleIndex = sourceAppointment.getRescheduleIndex();
        return updateIndex > rescheduleIndex ? updateIndex : rescheduleIndex;
    }

    public int getMinimumIndex(Appointment sourceAppointment) {
        int updateIndex = sourceAppointment.getUpdateIndex();
        int rescheduleIndex = sourceAppointment.getRescheduleIndex();
        return updateIndex < rescheduleIndex ? updateIndex : rescheduleIndex;
    }
}
