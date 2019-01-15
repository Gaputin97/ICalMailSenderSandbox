package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
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
        List<TimeSlot> newAppTimeSlots = appointment.getTimeSlots();
        List<TimeSlot> oldAppTimeSlots = oldAppointment.getTimeSlots();
        int newAppTimeSlotsMaxId = templateTimeSlotDefiner.defineHighestIdOfTimeSlots(newAppTimeSlots);
        int oldAppTimeSlotsMaxId = templateTimeSlotDefiner.defineHighestIdOfTimeSlots(oldAppTimeSlots);
        int commonMaxId;
        if (newAppTimeSlotsMaxId > oldAppTimeSlotsMaxId) {
            commonMaxId = newAppTimeSlotsMaxId;
        } else {
            commonMaxId = oldAppTimeSlotsMaxId;
        }
        StringBuilder timeSlots = new StringBuilder();
        for (int timeSlotId = 0; timeSlotId < commonMaxId; timeSlotId++) {
            TimeSlot newAppTimeSlot = newAppTimeSlots.get(timeSlotId);
            TimeSlot oldAppTimeSlot = oldAppTimeSlots.get(timeSlotId);
            if (newAppTimeSlot == null && oldAppTimeSlot != null) {
                timeSlots.append("<s>" + oldAppointment + "</s>" + " (was deleted)");
            } else if (oldAppTimeSlot == null && newAppTimeSlot != null) {
                timeSlots.append(newAppTimeSlot);
            } else if (oldAppointment != null && newAppTimeSlot != null) {
                if (oldAppointment.equals(newAppTimeSlot)) {
                    timeSlots.append(newAppTimeSlot + " (was not changed)");
                } else {
                    timeSlots.append(newAppTimeSlot + " (" + "rescheduled from " + oldAppTimeSlot);
                }
            }
        }
        template.setTimeSlots(timeSlots.toString());
    }


}


