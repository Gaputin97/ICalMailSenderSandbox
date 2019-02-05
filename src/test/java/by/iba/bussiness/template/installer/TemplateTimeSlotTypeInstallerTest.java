package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionType;
import by.iba.bussiness.calendar.session.TypedSession;
import by.iba.bussiness.template.TemplateTimeSlotDefiner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class TemplateTimeSlotTypeInstallerTest {

    @Mock
    private TemplateTimeSlotDefiner templateTimeSlotDefiner;
    @InjectMocks
    private TemplateTimeSlotTypeInstaller templateTimeSlotTypeInstaller;

    @Test
    public void testInstallTypedSessions() {
        //given
        Instant firstSessionStart = Instant.ofEpochMilli(300);
        Instant firstSessionEnd = Instant.ofEpochMilli(310);
        Instant secondSessionStart = Instant.ofEpochMilli(400);
        Instant secondSessionEnd = Instant.ofEpochMilli(410);
        Instant thirdSessionStart = Instant.ofEpochMilli(500);
        Instant thirdSessionEnd = Instant.ofEpochMilli(510);
        Instant fourthSessionStart = Instant.ofEpochMilli(600);
        Instant fourthSessionEnd = Instant.ofEpochMilli(610);
        Session firstSession = new Session(1, firstSessionStart, firstSessionEnd);
        Session secondSession = new Session(2, secondSessionStart, secondSessionEnd);
        Session thirdSession = new Session(3, thirdSessionStart, thirdSessionEnd);
        Session fourthSession = new Session(4, fourthSessionStart, fourthSessionEnd);
        List<Session> oldAppSessions = new ArrayList<>();
        Collections.addAll(oldAppSessions, firstSession, secondSession, thirdSession, fourthSession);
        Session secondNewSession = new Session(5, secondSessionStart, secondSessionEnd);
        Session thirdNotChangedSession = new Session(3, thirdSessionStart, thirdSessionEnd);
        Session fourthRescheduledSession = new Session(4, firstSessionStart, firstSessionEnd);
        List<Session> newAppSessions = new ArrayList<>();
        Collections.addAll(newAppSessions, secondNewSession, thirdNotChangedSession, fourthRescheduledSession);
        Appointment currentAppointment = new Appointment();
        currentAppointment.setSessionList(oldAppSessions);
        Appointment newAppointment = new Appointment();
        newAppointment.setSessionList(newAppSessions);
        int newAppSessionsMaxId = 5;
        int oldAppSessionsMaxId = 4;
        int newAppSessionsMinId = 2;
        int oldAppSessionsMinId = 1;
        //when
        when(templateTimeSlotDefiner.defineHighestIdOfSessions(newAppSessions)).thenReturn(newAppSessionsMaxId);
        when(templateTimeSlotDefiner.defineHighestIdOfSessions(oldAppSessions)).thenReturn(oldAppSessionsMaxId);
        when(templateTimeSlotDefiner.defineLowestIdOfSessions(newAppSessions)).thenReturn(newAppSessionsMinId);
        when(templateTimeSlotDefiner.defineLowestIdOfSessions(oldAppSessions)).thenReturn(oldAppSessionsMinId);
        when(templateTimeSlotDefiner.defineSessionById(1, newAppSessions)).thenReturn(null);
        when(templateTimeSlotDefiner.defineSessionById(1, oldAppSessions)).thenReturn(firstSession);
        when(templateTimeSlotDefiner.defineSessionById(2, newAppSessions)).thenReturn(null);
        when(templateTimeSlotDefiner.defineSessionById(2, oldAppSessions)).thenReturn(secondSession);
        when(templateTimeSlotDefiner.defineSessionById(3, newAppSessions)).thenReturn(thirdNotChangedSession);
        when(templateTimeSlotDefiner.defineSessionById(3, oldAppSessions)).thenReturn(thirdSession);
        when(templateTimeSlotDefiner.defineSessionById(4, newAppSessions)).thenReturn(fourthRescheduledSession);
        when(templateTimeSlotDefiner.defineSessionById(4, oldAppSessions)).thenReturn(fourthSession);
        when(templateTimeSlotDefiner.defineSessionById(5, newAppSessions)).thenReturn(secondNewSession);
        when(templateTimeSlotDefiner.defineSessionById(5, oldAppSessions)).thenReturn(null);
        List<TypedSession> typedSessions = templateTimeSlotTypeInstaller.installTypedSessions(newAppointment, currentAppointment);
        //then
        Assert.assertEquals(typedSessions.get(0).getSessionType(), SessionType.DELETED);
        Assert.assertEquals(typedSessions.get(1).getSessionType(), SessionType.DELETED);
        Assert.assertEquals(typedSessions.get(2).getSessionType(), SessionType.NOT_CHANGED);
        Assert.assertEquals(typedSessions.get(3).getSessionType(), SessionType.RESCHEDULED);
        Assert.assertEquals(typedSessions.get(4).getSessionType(), SessionType.NEW);


    }
}