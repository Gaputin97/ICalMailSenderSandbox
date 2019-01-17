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
        int newAppTimeSlotsMinId = templateTimeSlotDefiner.defineLowestIdOfTimeSlots(newAppTimeSlots);
        int oldAppTimeSlotsMinId = templateTimeSlotDefiner.defineLowestIdOfTimeSlots(oldAppTimeSlots);
        int commonMaxId;
        int commonMinId;
        commonMaxId = newAppTimeSlotsMaxId > oldAppTimeSlotsMaxId ? newAppTimeSlotsMaxId : oldAppTimeSlotsMaxId;
        commonMinId = newAppTimeSlotsMinId < oldAppTimeSlotsMinId ? newAppTimeSlotsMinId : oldAppTimeSlotsMinId;
        StringBuilder timeSlots = new StringBuilder();
        for (int timeSlotId = commonMinId; timeSlotId <= commonMaxId; timeSlotId++) {
            TimeSlot newAppTimeSlot = templateTimeSlotDefiner.defineTimeSlotWithId(timeSlotId, newAppTimeSlots);
            TimeSlot oldAppTimeSlot = templateTimeSlotDefiner.defineTimeSlotWithId(timeSlotId, oldAppTimeSlots);
            if (newAppTimeSlot == null && oldAppTimeSlot != null) {
                timeSlots.append("<s>" + oldAppTimeSlot.getStartDateTime() + oldAppTimeSlot.getEndDateTime() + "</s>" + " (was deleted)");
                timeSlots.append("<br>");
            } else if (oldAppTimeSlot == null && newAppTimeSlot != null) {
                timeSlots.append(newAppTimeSlot.getStartDateTime() + "<b> - </b>" + newAppTimeSlot.getEndDateTime() + " (new date)");
                timeSlots.append("<br>");
            } else if (oldAppointment != null && newAppTimeSlot != null) {
                if (oldAppTimeSlot.equals(newAppTimeSlot)) {
                    timeSlots.append(newAppTimeSlot.getStartDateTime() + "<b> - </b>" + newAppTimeSlot.getEndDateTime() + " (was not changed)");
                    timeSlots.append("<br>");
                } else {
                    timeSlots.append(newAppTimeSlot.getStartDateTime() + "<b> - </b>" + newAppTimeSlot.getEndDateTime()
                            + " (" + "rescheduled from " + oldAppTimeSlot.getStartDateTime() + "<b> - </b>" + oldAppTimeSlot.getEndDateTime() + " )");
                    timeSlots.append("<br>");
                }
            }
        }
        template.setTimeSlots(timeSlots.toString());
    }

    public void installTimeSlotsIfInvitation(Appointment appointment, Template template) {
        List<TimeSlot> newAppTimeSlots = appointment.getTimeSlots();
        StringBuilder timeSlots = new StringBuilder();
        newAppTimeSlots.forEach(x -> {
            timeSlots.append(timeSlots.append(x.getStartDateTime() + "<b> - </b>" + x.getEndDateTime() + " (new date)"));
            timeSlots.append("<br>");
        });
        template.setTimeSlots(timeSlots.toString());

    }


}


