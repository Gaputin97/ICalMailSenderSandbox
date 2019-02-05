package by.iba.bussiness.appointment.determiner;

import by.iba.bussiness.appointment.Appointment;
import org.springframework.stereotype.Component;

@Component
public class IndexDeterminer {

    public int getMaxIndex(Appointment currentAppointment) {
        int updateIndex = currentAppointment.getUpdateIndex();
        int rescheduleIndex = currentAppointment.getRescheduleIndex();
        return updateIndex > rescheduleIndex ? updateIndex : rescheduleIndex;
    }

    public int getMinIndex(Appointment currentAppointment) {
        int updateIndex = currentAppointment.getUpdateIndex();
        int rescheduleIndex = currentAppointment.getRescheduleIndex();
        return updateIndex < rescheduleIndex ? updateIndex : rescheduleIndex;
    }
}
