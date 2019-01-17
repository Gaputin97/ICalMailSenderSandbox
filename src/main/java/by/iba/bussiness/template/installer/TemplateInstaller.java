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

    public void installCommontPartsOfTemplate(Appointment appointment, Appointment oldAppointment, Template template) {
        template.setDescription(appointment.getDescription());
        template.setLocation(appointment.getLocation());
        template.setSummary(appointment.getSummary());
        template.setOwner(appointment.getOwner());
        if (oldAppointment == null) {
            templateTimeSlotInstaller.installTimeSlotsIfInvitation(appointment, template);
        } else {
            templateTimeSlotInstaller.installTimeSlots(appointment, oldAppointment, template);
        }

    }
}
