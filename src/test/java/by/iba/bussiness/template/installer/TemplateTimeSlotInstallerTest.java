package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionType;
import by.iba.bussiness.calendar.session.TypedSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TemplateTimeSlotInstallerTest {

    private static final String BR = "<br/>";

    @InjectMocks
    private TemplateTimeSlotInstaller templateTimeSlotInstaller;

    @Test
    public void testInstallSessionsIfInvitation() {
        //given
        String newDateType = SessionType.NEW.getStringType();
        Appointment appointment = new Appointment();
        Instant firstDateStart = new Date().toInstant();
        Instant secondDateStart = new Date().toInstant();
        Instant thirdDateStart = new Date().toInstant();
        Instant firstDateEnd = new Date().toInstant();
        Instant secondDateEnd = new Date().toInstant();
        Instant thirdDateEnd = new Date().toInstant();
        Session firstSession = new Session(1, firstDateStart, firstDateEnd);
        Session secondSession = new Session(2, secondDateStart, secondDateEnd);
        Session thirdSession = new Session(3, thirdDateStart, thirdDateEnd);
        String firstSessionString = firstSession.toString() + newDateType + BR;
        String secondSessionString = secondSession.toString() + newDateType + BR;
        String thirdSessionString = thirdSession.toString() + newDateType + BR;
        String commonSessionsString = firstSessionString + secondSessionString + thirdSessionString;
        List<Session> sessions = new ArrayList<>();
        appointment.setSessionList(sessions);
        Collections.addAll(sessions, firstSession, secondSession, thirdSession);
        //when
        String expected = templateTimeSlotInstaller.installSessionsIfInvitation(appointment);
        //then
        Assert.assertEquals(expected, commonSessionsString);


    }

    @Test
    public void installSessionsIfInvitation() {
        //given
        String deletedDateType = SessionType.DELETED.getStringType();
        String rescheduledDateType = SessionType.RESCHEDULED.getStringType();
        String notChangedDateType = SessionType.NOT_CHANGED.getStringType();
        Instant firstDateStart = new Date().toInstant();
        Instant secondDateStart = new Date().toInstant();
        Instant thirdDateStart = new Date().toInstant();
        Instant firstDateEnd = new Date().toInstant();
        Instant secondDateEnd = new Date().toInstant();
        Instant thirdDateEnd = new Date().toInstant();
        TypedSession firstTypedSession = new TypedSession(1, firstDateStart, firstDateEnd);
        firstTypedSession.setSessionType(SessionType.DELETED);
        TypedSession secondTypedSession = new TypedSession(2, secondDateStart, secondDateEnd);
        secondTypedSession.setSessionType(SessionType.NOT_CHANGED);
        TypedSession thirdTypedSession = new TypedSession(3, thirdDateStart, thirdDateEnd);
        thirdTypedSession.setSessionType(SessionType.RESCHEDULED);
        String firstSessionString = firstTypedSession.toString() + deletedDateType + BR;
        String secondSessionString = secondTypedSession.toString() + notChangedDateType + BR;
        String thirdSessionString = thirdTypedSession.toString() + rescheduledDateType + BR;
        List<TypedSession> typedSessions = new ArrayList<>();
        Collections.addAll(typedSessions, firstTypedSession, secondTypedSession, thirdTypedSession);
        String commonSessionsString = firstSessionString + secondSessionString + thirdSessionString;
        //when
        String expected = templateTimeSlotInstaller.installSessionsIfUpdate(typedSessions);
        //then
        Assert.assertEquals(expected, commonSessionsString);

    }
}