package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.AppointmentIndexHandler;
import by.iba.bussiness.calendar.creator.simple.SimpleMeetingCalendarTemplateCreator;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Method;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CalendarCreatorTest {
    @Mock
    private SimpleMeetingCalendarTemplateCreator simpleMeetingCalendarTemplateCreator;
    @Mock
    private AppointmentIndexHandler appointmentIndexHandler;
    @InjectMocks
    private CalendarCreator calendarCreator;

    @Test
    public void testCreateCalendarTemplateWhenEnrollmentStatusIsCancelled() {
        VEvent vEvent = new VEvent();
        Appointment appointment = new Appointment();
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CANCELLED.name());

        Calendar expectedCancelCalendar = new Calendar();
        expectedCancelCalendar.getProperties().add(Method.CANCEL);

        when(simpleMeetingCalendarTemplateCreator.createSimpleCancellationCalendar(vEvent)).thenReturn(expectedCancelCalendar);

        Calendar actualCalendar = calendarCreator.createConcreteCalendarTemplate(vEvent, enrollment, appointment);
        assertEquals("Calendars doesn't match", expectedCancelCalendar, actualCalendar);
    }

    @Test
    public void testCreateCalendarTemplateWhenEnrollmentStatusIsConfirmedAndCalendarVersionIsNull() {
        VEvent vEvent = new VEvent();
        Appointment appointment = new Appointment();
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CONFIRMED.name());
        enrollment.setCalendarVersion(null);

        Calendar expectedRequestCalendar = new Calendar();
        expectedRequestCalendar.getProperties().add(Method.REQUEST);

        when(simpleMeetingCalendarTemplateCreator.createSimpleCalendarTemplate(vEvent)).thenReturn(expectedRequestCalendar);

        Calendar actualCalendar = calendarCreator.createConcreteCalendarTemplate(vEvent, enrollment, appointment);
        assertEquals("Calendars doesn't match", expectedRequestCalendar, actualCalendar);
    }

    @Test
    public void testCreateCalendarTemplateWhenEnrollmentStatusIsConfirmedAndCalendarVersionIsNotNull() {
        VEvent vEvent = new VEvent();
        Appointment appointment = new Appointment();

        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CONFIRMED.name());
        enrollment.setCalendarVersion("2");

        Calendar expectedRequestCalendar = new Calendar();
        expectedRequestCalendar.getProperties().add(Method.REQUEST);

        when(simpleMeetingCalendarTemplateCreator.createSimpleCalendarTemplate(vEvent)).thenReturn(expectedRequestCalendar);
        when(appointmentIndexHandler.getMaxIndex(appointment)).thenReturn(3);

        Calendar actualCalendar = calendarCreator.createConcreteCalendarTemplate(vEvent, enrollment, appointment);
        assertEquals("Calendars doesn't match", expectedRequestCalendar, actualCalendar);
    }

    @Test
    public void testCreateCalendarTemplateWhenEnrollmentStatusIsConfirmedAndCalendarVersionIsNotNullAndHasSameCalendarIndex() {
        VEvent vEvent = new VEvent();
        Appointment appointment = new Appointment();

        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CONFIRMED.name());
        enrollment.setCalendarVersion("2");

        Calendar expectedRequestCalendar = new Calendar();
        expectedRequestCalendar.getProperties().add(Method.REQUEST);

        when(simpleMeetingCalendarTemplateCreator.createSimpleCalendarTemplate(vEvent)).thenReturn(expectedRequestCalendar);
        when(appointmentIndexHandler.getMaxIndex(appointment)).thenReturn(2);

        Calendar actualCalendar = calendarCreator.createConcreteCalendarTemplate(vEvent, enrollment, appointment);
        assertEquals("Calendars doesn't match", null, actualCalendar);
    }
}