package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.handler.AppointmentIndexesUpdater;
import by.iba.bussiness.appointment.handler.IndexDeterminer;
import by.iba.bussiness.calendar.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentIndexesUpdaterTest {
    @Mock
    private IndexDeterminer indexDeterminer;
    @InjectMocks
    private AppointmentIndexesUpdater appointmentHandler;

    @Test
    public void testUpdateAppointmentWhenAppointmentsEquals() {
        Appointment expectedAppointment = new Appointment();

        Appointment actualAppointment = appointmentHandler.updateIndexesBasedOnSessionsDifferences(expectedAppointment, expectedAppointment);

        assertEquals(expectedAppointment, actualAppointment);
    }

    @Test
    public void testUpdateAppointmentWhenAppointmentsNotEqualsAndSessionsAreNotEquals() {
        Session currentSession = new Session(1, Instant.now(), Instant.now().plusSeconds(1));
        List<Session> currentSessions = new ArrayList<>();
        currentSessions.add(currentSession);
        Appointment currentAppointment = new Appointment();
        currentAppointment.setSessionList(currentSessions);
        currentAppointment.setUpdateIndex(2);
        currentAppointment.setRescheduleIndex(1);

        Session newSession = new Session(1, Instant.now().plusSeconds(1), Instant.now().plusSeconds(2));
        List<Session> newSessions = new ArrayList<>();
        newSessions.add(newSession);
        Appointment newAppointment = new Appointment();
        newAppointment.setSessionList(newSessions);

        when(indexDeterminer.getMaxIndex(currentAppointment)).thenReturn(2);

        Appointment actualAppointment = appointmentHandler.updateIndexesBasedOnSessionsDifferences(newAppointment, currentAppointment);
        int actualUpdateIndex = actualAppointment.getUpdateIndex();
        int actualRescheduleIndex = actualAppointment.getRescheduleIndex();

        assertEquals("Error caused on different update indexes", 2, actualUpdateIndex);
        assertEquals("Error caused on different reschedule indexes", 3, actualRescheduleIndex);
    }

    @Test
    public void testUpdateAppointmentWhenAppointmentsNonEqualsAndSessionsAreEquals() {
        List<Session> sessionList = new ArrayList<>();
        Session session = new Session(1, Instant.now(), Instant.now().plusSeconds(1));
        sessionList.add(session);

        Appointment currentAppointment = new Appointment();
        currentAppointment.setUpdateIndex(1);
        currentAppointment.setRescheduleIndex(2);
        currentAppointment.setSessionList(sessionList);

        Appointment newAppointment = new Appointment();
        newAppointment.setDescription("New Description");
        newAppointment.setSessionList(sessionList);

        when(indexDeterminer.getMaxIndex(currentAppointment)).thenReturn(2);

        Appointment actualAppointment = appointmentHandler.updateIndexesBasedOnSessionsDifferences(newAppointment, currentAppointment);
        int actualUpdateIndex = actualAppointment.getUpdateIndex();
        int actualRescheduleIndex = actualAppointment.getRescheduleIndex();

        assertEquals("Error caused on different update indexes", 3, actualUpdateIndex);
        assertEquals("Error caused on different reschedule indexes", 2, actualRescheduleIndex);
    }
}