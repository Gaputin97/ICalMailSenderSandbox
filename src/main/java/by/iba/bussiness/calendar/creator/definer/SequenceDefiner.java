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
        Sequence sequence = new Sequence(appointmentHandler.getMaximumIndex(appointment));
        return sequence;
    }
}