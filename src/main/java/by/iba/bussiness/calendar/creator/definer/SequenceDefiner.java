package by.iba.bussiness.calendar.creator.definer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.enrollment.Enrollment;
import net.fortuna.ical4j.model.property.Sequence;

public class SequenceDefiner {
    public Sequence defineSequence(Appointment appointment, Enrollment enrollment) {
        Sequence sequence;
        if (enrollment != null) {
            if (appointment.getRescheduleIndex() > appointment.getUpdateIndex()) {
                Integer calendarVersionInteger = Integer.parseInt(enrollment.getCalendarVersion());
                ++calendarVersionInteger;
                String increasedCalanderVersion = calendarVersionInteger.toString();
                sequence = new Sequence(increasedCalanderVersion);
            } else {
                sequence = new Sequence(enrollment.getCalendarVersion());
            }
        } else {
            sequence = new Sequence("0");
        }
        return sequence;
    }
}
