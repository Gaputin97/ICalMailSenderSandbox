package by.iba.bussiness.appointment.determiner;

import by.iba.bussiness.appointment.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AppointmentDeterminer {

    public Appointment determineNewAppointmentByIndexes(Appointment newAppointmentWithIndexes, Appointment currentAppointment) {
        Appointment newAppointment;
        if ((newAppointmentWithIndexes.getUpdateIndex() == 0 && newAppointmentWithIndexes.getRescheduleIndex() == 0) ||
                (newAppointmentWithIndexes.getRescheduleIndex() > currentAppointment.getRescheduleIndex() ||
                        newAppointmentWithIndexes.getUpdateIndex() > currentAppointment.getUpdateIndex())) {
            newAppointment = newAppointmentWithIndexes;
        } else {
            newAppointment = currentAppointment;
        }
        return newAppointment;
    }
}