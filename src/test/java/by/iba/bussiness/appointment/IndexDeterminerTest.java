package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.determiner.IndexDeterminer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class IndexDeterminerTest {

    @InjectMocks
    private IndexDeterminer maxOrMinIndexDeterminer;

    @Test
    public void testGetMaximumIndexWhenRescheduleIndexGreater() {
        Appointment currentAppointment = new Appointment();
        currentAppointment.setUpdateIndex(1);
        currentAppointment.setRescheduleIndex(2);

        int actualIndex = maxOrMinIndexDeterminer.getMaxIndex(currentAppointment);
        assertEquals(2, actualIndex);
    }

    @Test
    public void testGetMaximumIndexWhenUpdateIndexGreater() {
        Appointment currentAppointment = new Appointment();
        currentAppointment.setUpdateIndex(2);
        currentAppointment.setRescheduleIndex(1);

        int actualIndex = maxOrMinIndexDeterminer.getMaxIndex(currentAppointment);
        assertEquals(2, actualIndex);
    }

    @Test
    public void getMinimumIndexWhenRescheduleIndexSmaller() {
        Appointment currentAppointment = new Appointment();
        currentAppointment.setUpdateIndex(2);
        currentAppointment.setRescheduleIndex(1);

        int actualIndex = maxOrMinIndexDeterminer.getMinIndex(currentAppointment);
        assertEquals(1, actualIndex);
    }

    @Test
    public void getMinimumIndexWhenUpdateIndexSmaller() {
        Appointment currentAppointment = new Appointment();
        currentAppointment.setUpdateIndex(1);
        currentAppointment.setRescheduleIndex(2);

        int actualIndex = maxOrMinIndexDeterminer.getMinIndex(currentAppointment);
        assertEquals(1, actualIndex);
    }
}