package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.TypedSession;
import by.iba.bussiness.template.Template;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemplateInstallerTest {

    @Mock
    private TemplateTimeSlotInstaller templateTimeSlotInstaller;
    @Mock
    private TemplateTimeSlotTypeInstaller templateTimeSlotTypeInstaller;
    @InjectMocks
    private TemplateInstaller templateInstaller;

    @Test
    public void installCommonPartsWhenOldAppIsNull() {
        //given
        Appointment appointment = new Appointment();
        Session firstSession = new Session(0, new Date().toInstant(), new Date().toInstant());
        Session secondSession = new Session(1, new Date().toInstant(), new Date().toInstant());
        List<Session> sessions = new ArrayList<>();
        sessions.add(firstSession);
        sessions.add(secondSession);
        appointment.setSessionList(sessions);
        String stringSessions = "Sessions";
        //when
        when(templateTimeSlotInstaller.installSessionsIfInvitation(appointment)).thenReturn(stringSessions);
        Template template = templateInstaller.installTemplate(appointment, null);
        String expected = template.getSessions();
        //then
        Assert.assertEquals(expected, stringSessions);
    }


    @Test
    public void installCommonPartsWhenOldAppIsNotNull() {
        //given
        Appointment appointment = new Appointment();
        Appointment currentAppointment = new Appointment();
        TypedSession firstSession = new TypedSession(0, new Date().toInstant(), new Date().toInstant());
        TypedSession secondSession = new TypedSession(1, new Date().toInstant(), new Date().toInstant());
        List<TypedSession> sessions = new ArrayList<>();
        sessions.add(firstSession);
        sessions.add(secondSession);
        String stringSessions = "Sessions";
        //when
        when(templateTimeSlotTypeInstaller.installTypedSessions(appointment, currentAppointment)).thenReturn(sessions);
        when(templateTimeSlotInstaller.installSessionsIfUpdate(sessions)).thenReturn(stringSessions);
        Template template = templateInstaller.installTemplate(appointment, currentAppointment);
        //then
        Assert.assertEquals(template.getSessions(), stringSessions);


    }
}

//    public Template installTemplate(Appointment appointment, Appointment currentAppointment) {
//        if (currentAppointment == null) {
//            sessions = templateTimeSlotInstaller.installSessionsIfInvitation(appointment);
//            template.setSessions(sessions);
//        } else {
//            List<Session> sessionsWithType = templateTimeSlotInstaller.installTypedSessions(appointment, currentAppointment);
//            sessions = templateTimeSlotInstaller.installSessionsIfUpdate(sessionsWithType);
//            template.setSessions(sessions);
//        }
//        return template;
//    }