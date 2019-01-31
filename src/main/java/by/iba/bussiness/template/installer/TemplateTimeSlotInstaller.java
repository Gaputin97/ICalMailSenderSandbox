package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.TypedSession;
import by.iba.bussiness.template.TemplateTimeSlotDefiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TemplateTimeSlotInstaller {

    private TemplateTimeSlotDefiner templateTimeSlotDefiner;

    @Autowired
    public TemplateTimeSlotInstaller(TemplateTimeSlotDefiner templateTimeSlotDefiner) {
        this.templateTimeSlotDefiner = templateTimeSlotDefiner;
    }

    public String installSessionsIfUpdate(List<TypedSession> typedSessions) {
        StringBuilder dates = new StringBuilder();
        typedSessions.stream().sorted().forEach(session -> {
            dates.append(session.toString() + session.getSessionType().getStringType()).append("<br/>");
        });
        return dates.toString();
    }

    public String installSessionsIfInvitation(Appointment appointment) {
        List<Session> newAppSessions = new ArrayList<>(appointment.getSessionList());
        StringBuilder sessionsBuilder = new StringBuilder();
        newAppSessions.stream().sorted().forEach(session ->
                sessionsBuilder.append(session.toString()).append("<br/>"));
        return sessionsBuilder.toString();
    }
}


