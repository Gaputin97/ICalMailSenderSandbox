package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TemplateInstaller {

    private TemplateTimeSlotInstaller templateTimeSlotInstaller;

    @Autowired
    public TemplateInstaller(TemplateTimeSlotInstaller templateTimeSlotInstaller) {
        this.templateTimeSlotInstaller = templateTimeSlotInstaller;
    }

    public Template installCommonPartsOfTemplate(Appointment appointment, Appointment oldAppointment) {
        Template template = new Template();
        template.setDescription(appointment.getDescription());
        template.setLocation(appointment.getLocation());
        template.setSummary(appointment.getSummary());
        template.setFrom(appointment.getFrom());
        template.setFromName(appointment.getFromName());
        String sessions;
        if (oldAppointment == null) {
            sessions = templateTimeSlotInstaller.installSessionsIfInvitation(appointment);
            template.setSessions(sessions);
        } else {
            List<Session> sessionsWithType = templateTimeSlotInstaller.installSessionsType(appointment, oldAppointment);
            sessions = templateTimeSlotInstaller.installSessionsByType(sessionsWithType);
            template.setSessions(sessions);
        }
        return template;
    }
}
