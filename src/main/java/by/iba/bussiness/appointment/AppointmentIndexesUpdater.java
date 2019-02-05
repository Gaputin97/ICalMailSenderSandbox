package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.determiner.IndexDeterminer;
import by.iba.bussiness.calendar.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class AppointmentIndexesUpdater {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentIndexesUpdater.class);
    private IndexDeterminer indexDeterminer;

    @Autowired
    public AppointmentIndexesUpdater(IndexDeterminer indexDeterminer) {
        this.indexDeterminer = indexDeterminer;
    }

    public Appointment updateIndexes(Appointment newAppointment, Appointment currentAppointment) {
        if (currentAppointment.equals(newAppointment)) {
            newAppointment = currentAppointment;
        } else {
            List<Session> currentSessions = currentAppointment.getSessionList();
            List<Session> newSessions = newAppointment.getSessionList();
            int maximumIndex = indexDeterminer.getMaxIndex(currentAppointment);
            if (!currentSessions.equals(newSessions)) {
                int currentUpdatedIndex = currentAppointment.getUpdateIndex();
                newAppointment.setUpdateIndex(currentUpdatedIndex);
                newAppointment.setRescheduleIndex(++maximumIndex);
            } else {
                int currentRescheduleIndex = currentAppointment.getRescheduleIndex();
                newAppointment.setRescheduleIndex(currentRescheduleIndex);
                newAppointment.setUpdateIndex(++maximumIndex);
            }
        }
        BigInteger appointmentId = currentAppointment.getId();
        newAppointment.setId(appointmentId);
        return newAppointment;
    }
}
