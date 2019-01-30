package by.iba.bussiness.calendar.creator.definer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import net.fortuna.ical4j.model.property.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequenceDefiner {
    private AppointmentHandler appointmentHandler;
    @Autowired
    public SequenceDefiner(AppointmentHandler appointmentHandler) {
        this.appointmentHandler = appointmentHandler;
    }

    public Sequence defineSequence(Appointment newAppointment) {
        Sequence sequence;
        int maxIndex = appointmentHandler.getMaxIndex(newAppointment);
        int updatedIndex = newAppointment.getUpdateIndex();
        int minIndex = appointmentHandler.getMinIndex(newAppointment);
        if (maxIndex == updatedIndex) {
            sequence = new Sequence(minIndex);
        } else {
            sequence = new Sequence(maxIndex);
        }
        return sequence;
    }
}