package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
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

    @InjectMocks
    private TemplateTimeSlotInstaller templateTimeSlotInstaller;

    @Test
    public void testInstallSessionsIfInvitation() {
        //given
        String newDate = " (new date)<br/>";
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
        String firstSessionString = firstSession.toString() + newDate;
        String secondSessionString = secondSession.toString() + newDate;
        String thirdSessionString = thirdSession.toString() + newDate;
        String commonSessionsString = firstSessionString + secondSessionString + thirdSessionString;
        List<Session> sessions = new ArrayList<>();
        appointment.setSessionList(sessions);
        Collections.addAll(sessions, firstSession, secondSession, thirdSession);
        //when
        String stringSessions = templateTimeSlotInstaller.installSessionsIfInvitation(appointment);
        //then
        Assert.assertEquals(stringSessions, commonSessionsString);


    }

    @Test
    public void installSessionsIfInvitation() {
    }
}