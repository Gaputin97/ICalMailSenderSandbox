package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.determiner.IndexDeterminer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IndexDeterminerTest {
    private IndexDeterminer indexDeterminer = new IndexDeterminer();

    @Test
    public void testGetMaximumIndexWhenRescheduleIndexGreater() {
        Appointment currentAppointment = new Appointment();
        currentAppointment.setUpdateIndex(1);
        currentAppointment.setRescheduleIndex(2);

        int actualIndex = indexDeterminer.getMaxIndex(currentAppointment);

        assertEquals(2, actualIndex);
    }

    @Test
    public void testGetMaximumIndexWhenUpdateIndexGreater() {
        Appointment currentAppointment = new Appointment();
        currentAppointment.setUpdateIndex(2);
        currentAppointment.setRescheduleIndex(1);

        int actualIndex = indexDeterminer.getMaxIndex(currentAppointment);

        assertEquals(2, actualIndex);
    }

    @Test
    public void testGetMinimumIndexWhenRescheduleIndexSmaller() {
        Appointment currentAppointment = new Appointment();
        currentAppointment.setUpdateIndex(2);
        currentAppointment.setRescheduleIndex(1);

        int actualIndex = indexDeterminer.getMinIndex(currentAppointment);

        assertEquals(1, actualIndex);
    }

    @Test
    public void testGetMinimumIndexWhenUpdateIndexSmaller() {
        Appointment currentAppointment = new Appointment();
        currentAppointment.setUpdateIndex(1);
        currentAppointment.setRescheduleIndex(2);

        int actualIndex = indexDeterminer.getMinIndex(currentAppointment);

        assertEquals(1, actualIndex);
    }
}