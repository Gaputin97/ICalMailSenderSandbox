package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        template.setOwner(appointment.getOwner());
        String sessions;
        if (oldAppointment == null) {
            sessions = templateTimeSlotInstaller.installSessionsIfInvitation(appointment);
            template.setSessions(sessions);
        } else {
            sessions = templateTimeSlotInstaller.installSessions(appointment, oldAppointment);
            template.setSessions(sessions);
        }
        return template;
    }
}
