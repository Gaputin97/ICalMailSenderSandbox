package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionType;
import by.iba.bussiness.calendar.session.TypedSession;
import by.iba.bussiness.template.TemplateTimeSlotDefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TemplateTimeSlotTypeInstaller {

    @Autowired
    private TemplateTimeSlotDefiner templateTimeSlotDefiner;


    public List<TypedSession> installTypedSessions(Appointment appointment, Appointment oldAppointment) {
        List<Session> newAppSessions = appointment.getSessionList();
        List<Session> oldAppSessions = oldAppointment.getSessionList();

        int newAppSessionsMaxId = templateTimeSlotDefiner.defineHighestIdOfSessions(newAppSessions);
        int oldAppSessionsMaxId = templateTimeSlotDefiner.defineHighestIdOfSessions(oldAppSessions);
        int newAppSessionsMinId = templateTimeSlotDefiner.defineLowestIdOfSessions(newAppSessions);
        int oldAppSessionsMinId = templateTimeSlotDefiner.defineLowestIdOfSessions(oldAppSessions);

        int commonMaxId = newAppSessionsMaxId > oldAppSessionsMaxId ? newAppSessionsMaxId : oldAppSessionsMaxId;
        int commonMinId = newAppSessionsMinId < oldAppSessionsMinId ? newAppSessionsMinId : oldAppSessionsMinId;

        List<TypedSession> typedSessions = new ArrayList<>();
        for (int sessionId = commonMinId; sessionId <= commonMaxId; sessionId++) {
            Session newAppSession = templateTimeSlotDefiner.defineSessionById(sessionId, newAppSessions);
            Session oldAppSession = templateTimeSlotDefiner.defineSessionById(sessionId, oldAppSessions);
            if (newAppSession == null && oldAppSession != null) {
                TypedSession typedSession = new TypedSession(oldAppSession.getId(), oldAppSession.getStartDateTime(), oldAppSession.getEndDateTime());
                typedSession.setSessionType(SessionType.DELETED);
                typedSessions.add(typedSession);
            } else if (oldAppSession == null && newAppSession != null) {
                TypedSession typedSession = new TypedSession(newAppSession.getId(), newAppSession.getStartDateTime(), newAppSession.getEndDateTime());
                typedSession.setSessionType(SessionType.NEW);
                typedSessions.add(typedSession);
            } else if (oldAppSession != null && newAppSession != null) {
                if (oldAppSession.equals(newAppSession)) {
                    TypedSession typedSession = new TypedSession(newAppSession.getId(), newAppSession.getStartDateTime(), newAppSession.getEndDateTime());
                    typedSession.setSessionType(SessionType.NOT_CHANGED);
                    typedSessions.add(typedSession);
                } else {
                    TypedSession typedSession = new TypedSession(newAppSession.getId(), newAppSession.getStartDateTime(), newAppSession.getEndDateTime());
                    typedSession.setSessionType(SessionType.RESCHEDULED);
                    typedSessions.add(typedSession);
                }
            }
        }
        return typedSessions;
    }
}
