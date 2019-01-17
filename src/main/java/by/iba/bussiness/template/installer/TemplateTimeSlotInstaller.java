package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
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

    public void installTimeSlots(Appointment appointment, Appointment oldAppointment, Template template) {
//        List<Session> newAppSessions = appointment.getSessionList();
//        List<Session> oldAppSessions = oldAppointment.getSessionList();
//
//        int newAppSessionsMaxId = templateTimeSlotDefiner.defineHighestIdOfSessions(newAppSessions);
//        int oldAppSessionsMaxId = templateTimeSlotDefiner.defineHighestIdOfSessions(oldAppSessions);
//        int newAppSessionsMinId = templateTimeSlotDefiner.defineLowestIdOfSessions(newAppSessions);
//        int oldAppSessionsMinId = templateTimeSlotDefiner.defineLowestIdOfSessions(oldAppSessions);
//
//        int commonMaxId = newAppSessionsMaxId > oldAppSessionsMaxId ? newAppSessionsMaxId : oldAppSessionsMaxId;
//        int commonMinId = newAppSessionsMinId < oldAppSessionsMinId ? newAppSessionsMinId : oldAppSessionsMinId;
//
//        StringBuilder sessionsBuilder = new StringBuilder();
//        for (int sessionId = commonMinId; sessionId <= commonMaxId; sessionId++) {
//            TimeSlot newAppTimeSlot = templateTimeSlotDefiner.defineTimeSlotWithId(sessionId, newAppTimeSlots);
//            TimeSlot oldAppTimeSlot = templateTimeSlotDefiner.defineTimeSlotWithId(sessionId, oldAppTimeSlots);
//            Session newAppSession = optionalNewAppSessions.orElse(null);
//            Session oldAppSession = optionalOldAppSessions.orElse(null);
//
//            if (newAppSession == null && oldAppSession != null) {
//                sessionsBuilder.append("<s>")
//                        .append(oldAppSession.getStartDateTime())
//                        .append(oldAppSession.getEndDateTime())
//                        .append("</s>(was deleted)<br>");
//            } else if (oldAppSession == null && newAppSession != null) {
//                sessionsBuilder.append(newAppSession.toString())
//                        .append(" (was added)");
//            } else if (newAppSession != null) {
//                if (oldAppSession.equals(newAppSession)) {
//                    sessionsBuilder.append(newAppSession.toString())
//                            .append(" (was not changed)<br>");
//                } else {
//                    sessionsBuilder.append(newAppSession.toString())
//                            .append(" (rescheduled from ")
//                            .append(oldAppSession.toString())
//                            .append(" )<br>");
//                }
//            }
//        }
//        template.setSessions(sessionsBuilder.toString());
    }

    public void installTimeSlotsIfInvitation(Appointment appointment, Template template) {
        List<Session> newAppSessions = appointment.getSessionList();
        StringBuilder sessions = new StringBuilder();
        newAppSessions.forEach(session -> sessions.append(session.toString()).append(" (was added)<br>"));
        template.setSessions(sessions.toString());
    }
}


