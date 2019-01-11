package by.iba.bussiness.calendar;

import by.iba.bussiness.appointment.Appointment;
import org.springframework.stereotype.Component;

@Component
public class ComplexCalendarTimeSlotsComparator {
    public boolean isIdential(Appointment oldAppointment, Appointment newAppointment) {
        boolean isIdential;
        if(oldAppointment.getTimeSlots().size() != newAppointment.getTimeSlots().size()) {

        }
    }
}
