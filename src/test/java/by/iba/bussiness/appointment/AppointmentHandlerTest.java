package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.handler.AppointmentHandler;
import by.iba.bussiness.appointment.handler.AppointmentIndexHandler;
import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentHandlerTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private AppointmentCreator appointmentCreator;

    @InjectMocks
    private AppointmentHandler appointmentHandler;

    @Mock
    private AppointmentIndexHandler appointmentIndexHandler;

    @Test
    public void testUpdateAppointmentWhenAppointmentsEquals() {
        Meeting meeting = new Meeting();
        meeting.setId(new BigInteger("3"));
        InvitationTemplate invitationTemplate = new InvitationTemplate();
        Appointment expectedAppointment = new Appointment();

        when(appointmentCreator.createAppointment(meeting, invitationTemplate)).thenReturn(expectedAppointment);
        when(appointmentRepository.getByMeetingId(new BigInteger("3"))).thenReturn(expectedAppointment);

        Appointment actualAppointment = appointmentHandler.updateAppointment(meeting, invitationTemplate);

        assertEquals(expectedAppointment, actualAppointment);
    }

    @Test
    public void testUpdateAppointmentWhenAppointmentsNonEqualsAndSessionsAreNotEquals() {
        Meeting meeting = new Meeting();
        meeting.setId(new BigInteger("3"));
        InvitationTemplate invitationTemplate = new InvitationTemplate();

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

        when(appointmentCreator.createAppointment(meeting, invitationTemplate)).thenReturn(newAppointment);
        when(appointmentRepository.getByMeetingId(new BigInteger("3"))).thenReturn(currentAppointment);
        when(appointmentIndexHandler.getMaxIndex(currentAppointment)).thenReturn(2);

        Appointment actualAppointment = appointmentHandler.updateAppointment(meeting, invitationTemplate);
        int actualUpdateIndex = actualAppointment.getUpdateIndex();
        int actualRescheduleIndex = actualAppointment.getRescheduleIndex();

        assertEquals("Error caused on different update indexes", 2, actualUpdateIndex);
        assertEquals("Error caused on different reschedule indexes", 3, actualRescheduleIndex);
    }

    @Test
    public void testUpdateAppointmentWhenAppointmentsNonEqualsAndSessionsAreEquals() {
        Meeting meeting = new Meeting();
        meeting.setId(new BigInteger("3"));
        InvitationTemplate invitationTemplate = new InvitationTemplate();

        List<Session> sessionList = new ArrayList<>();
        Session session = new Session(1, Instant.now(), Instant.now().plusSeconds(1));
        sessionList.add(session);

        Appointment currentAppointment = new Appointment();
        currentAppointment.setSessionList(sessionList);
        currentAppointment.setUpdateIndex(1);
        currentAppointment.setRescheduleIndex(2);

        Appointment newAppointment = new Appointment();
        newAppointment.setDescription("New Description");
        newAppointment.setSessionList(sessionList);

        when(appointmentCreator.createAppointment(meeting, invitationTemplate)).thenReturn(newAppointment);
        when(appointmentRepository.getByMeetingId(new BigInteger("3"))).thenReturn(currentAppointment);
        when(appointmentIndexHandler.getMaxIndex(currentAppointment)).thenReturn(2);

        Appointment actualAppointment = appointmentHandler.updateAppointment(meeting, invitationTemplate);
        int actualUpdateIndex = actualAppointment.getUpdateIndex();
        int actualRescheduleIndex = actualAppointment.getRescheduleIndex();

        assertEquals("Error caused on different update indexes", 3, actualUpdateIndex);
        assertEquals("Error caused on different reschedule indexes", 2, actualRescheduleIndex);
    }

}