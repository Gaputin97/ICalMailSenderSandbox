package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionType;
import by.iba.bussiness.template.TemplateTimeSlotDefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TemplateTimeSlotInstaller {

    private TemplateTimeSlotDefiner templateTimeSlotDefiner;

    @Autowired
    public TemplateTimeSlotInstaller(TemplateTimeSlotDefiner templateTimeSlotDefiner) {
        this.templateTimeSlotDefiner = templateTimeSlotDefiner;
    }


    public List<Session> installSessionsType(Appointment appointment, Appointment oldAppointment) {
        List<Session> newAppSessions = appointment.getSessionList();
        List<Session> oldAppSessions = oldAppointment.getSessionList();

        int newAppSessionsMaxId = templateTimeSlotDefiner.defineHighestIdOfSessions(newAppSessions);
        int oldAppSessionsMaxId = templateTimeSlotDefiner.defineHighestIdOfSessions(oldAppSessions);
        int newAppSessionsMinId = templateTimeSlotDefiner.defineLowestIdOfSessions(newAppSessions);
        int oldAppSessionsMinId = templateTimeSlotDefiner.defineLowestIdOfSessions(oldAppSessions);

        int commonMaxId = newAppSessionsMaxId > oldAppSessionsMaxId ? newAppSessionsMaxId : oldAppSessionsMaxId;
        int commonMinId = newAppSessionsMinId < oldAppSessionsMinId ? newAppSessionsMinId : oldAppSessionsMinId;

        List<Session> sessionsWithType = new ArrayList<>();
        for (int sessionId = commonMinId; sessionId <= commonMaxId; sessionId++) {
            Session newAppSession = templateTimeSlotDefiner.defineSessionById(sessionId, newAppSessions);
            Session oldAppSession = templateTimeSlotDefiner.defineSessionById(sessionId, oldAppSessions);
            if (newAppSession == null && oldAppSession != null) {
                oldAppSession.setSessionType(SessionType.DELETED);
                sessionsWithType.add(oldAppSession);
            } else if (oldAppSession == null && newAppSession != null) {
                newAppSession.setSessionType(SessionType.NEW);
                sessionsWithType.add(newAppSession);
            } else if (oldAppSession != null && newAppSession != null) {
                if (oldAppSession.equals(newAppSession)) {
                    newAppSession.setSessionType(SessionType.NOT_CHANGED);
                    sessionsWithType.add(newAppSession);
                } else {
                    newAppSession.setSessionType(SessionType.RESCHEDULED);
                    sessionsWithType.add(newAppSession);
                }
            }
        }
        return sessionsWithType;
    }

    public String installSessionsByType(List<Session> sessionsWithTypes) {
        Collections.sort(sessionsWithTypes);
        StringBuilder dates = new StringBuilder();
        sessionsWithTypes.forEach(session -> {
            dates.append(session.toString() + session.getSessionType()).append("<br/>");
        });
        return dates.toString();

    }

    public String installSessionsIfInvitation(Appointment appointment) {
        List<Session> newAppSessions = new ArrayList<>(appointment.getSessionList());
        Collections.sort(newAppSessions);
        StringBuilder sessionsBuilder = new StringBuilder();
        newAppSessions.forEach(session -> sessionsBuilder.append(session.toString()).append(" (new date)<br/>"));
        return sessionsBuilder.toString();
    }
}


