package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.TypedSession;
import by.iba.bussiness.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TemplateInstaller {
    private TemplateTimeSlotInstaller templateTimeSlotInstaller;
    private TemplateTimeSlotTypeInstaller templateTimeSlotTypeInstaller;

    @Autowired
    public TemplateInstaller(TemplateTimeSlotInstaller templateTimeSlotInstaller,
                             TemplateTimeSlotTypeInstaller templateTimeSlotTypeInstaller) {
        this.templateTimeSlotInstaller = templateTimeSlotInstaller;
        this.templateTimeSlotTypeInstaller = templateTimeSlotTypeInstaller;
    }

    public Template installTemplate(Appointment appointment, Appointment currentAppointment) {
        Template template = new Template();
        template.setDescription(appointment.getDescription());
        template.setLocation(appointment.getLocation());
        template.setSummary(appointment.getSummary());
        template.setFrom(appointment.getFrom());
        template.setFromName(appointment.getFromName());
        String sessions;
        if (currentAppointment == null) {
            sessions = templateTimeSlotInstaller.installSessionsIfInvitation(appointment);
            template.setSessions(sessions);
        } else {
            List<TypedSession> typedSessions = templateTimeSlotTypeInstaller.installTypedSessions(appointment, currentAppointment);
            sessions = templateTimeSlotInstaller.installSessionsIfUpdate(typedSessions);
            template.setSessions(sessions);
        }
        return template;
    }
}
