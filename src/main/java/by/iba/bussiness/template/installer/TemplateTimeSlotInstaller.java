package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.template.Template;
import by.iba.bussiness.template.TemplateTimeSlotDefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TemplateTimeSlotInstaller {

    private TemplateTimeSlotDefiner templateTimeSlotDefiner;

    @Autowired
    public TemplateTimeSlotInstaller(TemplateTimeSlotDefiner templateTimeSlotDefiner) {
        this.templateTimeSlotDefiner = templateTimeSlotDefiner;
    }

    public String installSessions(Appointment appointment, Appointment oldAppointment) {
        List<Session> newAppSessions = appointment.getSessionList();
        List<Session> oldAppSessions = oldAppointment.getSessionList();

        int newAppSessionsMaxId = templateTimeSlotDefiner.defineHighestIdOfSessions(newAppSessions);
        int oldAppSessionsMaxId = templateTimeSlotDefiner.defineHighestIdOfSessions(oldAppSessions);
        int newAppSessionsMinId = templateTimeSlotDefiner.defineLowestIdOfSessions(newAppSessions);
        int oldAppSessionsMinId = templateTimeSlotDefiner.defineLowestIdOfSessions(oldAppSessions);

        int commonMaxId = newAppSessionsMaxId > oldAppSessionsMaxId ? newAppSessionsMaxId : oldAppSessionsMaxId;
        int commonMinId = newAppSessionsMinId < oldAppSessionsMinId ? newAppSessionsMinId : oldAppSessionsMinId;

        StringBuilder sessionsBuilder = new StringBuilder();
        for (int sessionId = commonMinId; sessionId <= commonMaxId; sessionId++) {
            Session newAppSession = templateTimeSlotDefiner.defineSessionById(sessionId, newAppSessions);
            Session oldAppSession = templateTimeSlotDefiner.defineSessionById(sessionId, oldAppSessions);
            if (newAppSession == null && oldAppSession != null) {
                sessionsBuilder.append("<s>")
                        .append(oldAppSession.toString())
                        .append("</s>(was deleted)<br>");
            } else if (oldAppSession == null && newAppSession != null) {
                sessionsBuilder.append(newAppSession.toString())
                        .append(" (was added)");
            } else if (newAppSession != null) {
                if (oldAppSession.equals(newAppSession)) {
                    sessionsBuilder.append(newAppSession.toString())
                            .append(" (was not changed)<br>");
                } else {
                    sessionsBuilder.append(newAppSession.toString())
                            .append(" (rescheduled from ")
                            .append(oldAppSession.toString())
                            .append(" )<br>");
                }
            }
        }
        return sessionsBuilder.toString();
    }

    public String installSessionsIfInvitation(Appointment appointment) {
        List<Session> newAppSessions = appointment.getSessionList();
        StringBuilder sessionsBuilder = new StringBuilder();
        newAppSessions.forEach(session -> sessionsBuilder.append(session.toString()).append(" (was added)<br>"));
        return sessionsBuilder.toString();
    }
}


