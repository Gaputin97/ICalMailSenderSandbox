package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
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


    public String installSessionsIfUpdate(List<Session> sessionsWithTypes) {
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


