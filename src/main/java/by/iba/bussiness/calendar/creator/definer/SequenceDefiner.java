package by.iba.bussiness.calendar.creator.definer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.AppointmentIndexHandler;
import net.fortuna.ical4j.model.property.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequenceDefiner {
    private AppointmentIndexHandler appointmentIndexHandler;

    @Autowired
    public SequenceDefiner(AppointmentIndexHandler appointmentIndexHandler) {
        this.appointmentIndexHandler = appointmentIndexHandler;
    }

    public Sequence defineSequence(Appointment newAppointment) {
        Sequence sequence;
        int maxIndex = appointmentIndexHandler.getMaxIndex(newAppointment);
        int updatedIndex = newAppointment.getUpdateIndex();
        int minIndex = appointmentIndexHandler.getMinIndex(newAppointment);
        if (maxIndex == updatedIndex) {
            sequence = new Sequence(minIndex);
        } else {
            sequence = new Sequence(maxIndex);
        }
        return sequence;
    }
}