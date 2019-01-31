package by.iba.bussiness.calendar.creator.definer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.IndexDeterminer;
import net.fortuna.ical4j.model.property.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequenceDefiner {
    private IndexDeterminer indexDeterminer;

    @Autowired
    public SequenceDefiner(IndexDeterminer indexDeterminer) {
        this.indexDeterminer = indexDeterminer;
    }

    public Sequence defineSequence(Appointment newAppointment) {
        Sequence sequence;
        int maxIndex = indexDeterminer.getMaxIndex(newAppointment);
        int updatedIndex = newAppointment.getUpdateIndex();
        int minIndex = indexDeterminer.getMinIndex(newAppointment);
        if (maxIndex == updatedIndex) {
            sequence = new Sequence(minIndex);
        } else {
            sequence = new Sequence(maxIndex);
        }
        return sequence;
    }
}