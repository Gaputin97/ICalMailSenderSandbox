package by.iba.bussiness.appointment.handler;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentCreator;
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
    private AppointmentIndexHandler appointmentIndexHandler;

    @Autowired
    public AppointmentHandler(AppointmentRepository appointmentRepository,
                              AppointmentCreator appointmentCreator,
                              AppointmentIndexHandler appointmentIndexHandler) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentCreator = appointmentCreator;
        this.appointmentIndexHandler = appointmentIndexHandler;
    }

    public Appointment updateAppointment(Meeting newMeeting, InvitationTemplate newTemplate) {
        Appointment newAppointment = appointmentCreator.createAppointment(newMeeting, newTemplate);
        Appointment currentAppointment = appointmentRepository.getByMeetingId(newMeeting.getId());

        int currentUpdatedIndex = currentAppointment.getUpdateIndex();
        int currentRescheduleIndex = currentAppointment.getRescheduleIndex();

        if (currentAppointment.equals(newAppointment)) {
            newAppointment = currentAppointment;
        } else {
            int maximumIndex = appointmentIndexHandler.getMaxIndex(currentAppointment);
            List<Session> currentSessions = currentAppointment.getSessionList();
            List<Session> newSessions = newAppointment.getSessionList();
            if (!currentSessions.equals(newSessions)) {
                newAppointment.setUpdateIndex(currentUpdatedIndex);
                newAppointment.setRescheduleIndex(++maximumIndex);
            } else {
                newAppointment.setRescheduleIndex(currentRescheduleIndex);
                newAppointment.setUpdateIndex(++maximumIndex);
            }
        }
        BigInteger updatedAppId = currentAppointment.getId();
        newAppointment.setId(updatedAppId);
        return newAppointment;
    }
}
