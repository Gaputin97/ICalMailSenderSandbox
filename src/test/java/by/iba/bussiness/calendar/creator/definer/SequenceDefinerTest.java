package by.iba.bussiness.calendar.creator.definer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import net.fortuna.ical4j.model.property.Sequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SequenceDefinerTest {
    @Mock
    private AppointmentHandler appointmentHandler;

    @InjectMocks
    private SequenceDefiner sequenceDefiner;

    @Test
    public void testSequenceWhenMaxIndexEqualsMinIndex() {
        Appointment newAppointment = mock(Appointment.class);
        int maxIndex = 2;
        int updatedIndex = 2;
        int minIndex = 3;
        Sequence expectedSequence = new Sequence(minIndex);

        when(appointmentHandler.getMaxIndex(newAppointment)).thenReturn(maxIndex);
        when(appointmentHandler.getMinIndex(newAppointment)).thenReturn(minIndex);
        when(newAppointment.getUpdateIndex()).thenReturn(updatedIndex);

        Sequence actualSequence = sequenceDefiner.defineSequence(newAppointment);

        assertEquals(expectedSequence, actualSequence);
    }

    @Test
    public void testSequenceWhenMaxIndexNotEqualsMinIndex() {
        Appointment newAppointment = mock(Appointment.class);
        int maxIndex = 3;
        int updatedIndex = 2;
        int minIndex = 2;
        Sequence expectedSequence = new Sequence(maxIndex);

        when(appointmentHandler.getMaxIndex(newAppointment)).thenReturn(maxIndex);
        when(appointmentHandler.getMinIndex(newAppointment)).thenReturn(minIndex);
        when(newAppointment.getUpdateIndex()).thenReturn(updatedIndex);

        Sequence actualSequence = sequenceDefiner.defineSequence(newAppointment);

        assertEquals(expectedSequence, actualSequence);
    }
}