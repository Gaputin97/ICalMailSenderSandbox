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

    public Sequence defineSequence(Appointment appointment) {
        Sequence sequence;
        int maximumIndex = appointmentHandler.getMaximumIndex(appointment);
        int updatedIndex = appointment.getUpdateIndex();
        int minimumIndex = appointmentHandler.getMinimumIndex(appointment);
        if (maximumIndex == updatedIndex) {

            sequence = new Sequence(minimumIndex);
        } else {
            sequence = new Sequence(maximumIndex);
        }
        return sequence;
    }
}