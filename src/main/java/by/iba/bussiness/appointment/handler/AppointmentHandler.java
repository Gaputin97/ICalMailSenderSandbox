package by.iba.bussiness.appointment.handler;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class AppointmentHandler {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentHandler.class);
    private IndexDeterminer indexDeterminer;

    @Autowired
    public AppointmentHandler(IndexDeterminer indexDeterminer) {
        this.indexDeterminer = indexDeterminer;
    }

    public Appointment updateAppointmentIndex(Appointment newAppointment, Appointment currentAppointment) {
        int currentUpdatedIndex = currentAppointment.getUpdateIndex();
        int currentRescheduleIndex = currentAppointment.getRescheduleIndex();

        if (currentAppointment.equals(newAppointment)) {
            newAppointment = currentAppointment;
        } else {
            int maximumIndex = indexDeterminer.getMaxIndex(currentAppointment);
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
