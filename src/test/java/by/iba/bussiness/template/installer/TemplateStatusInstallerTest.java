package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.template.TemplateType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemplateStatusInstallerTest {

    @Mock
    private AppointmentHandler appointmentHandler;
    @InjectMocks
    private TemplateStatusInstaller templateStatusInstaller;

    @Before
    public void setUp() {
        templateStatusInstaller = new TemplateStatusInstaller(appointmentHandler);
    }

    @Test
    public void testInstallTypeWhenEnrollmentStatusIsCancelled() {
        //given
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CANCELLED.toString());
        //when
        String expected = templateStatusInstaller.installTemplateType(enrollment, null);
        //then
        Assert.assertEquals(expected, TemplateType.CANCELLATION.toString());
    }

    @Test
    public void testInstallTypeWhenEnrollmentCalendarVersionIsNull() {
        //given
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CONFIRMED.toString());
        enrollment.setCalendarVersion(null);
        //when
        String expected = templateStatusInstaller.installTemplateType(enrollment, null);
        //then
        Assert.assertEquals(expected, TemplateType.INVITATION.toString());
    }

    @Test
    public void testInstallTypeWhenMaximumAppIndexMoreThenCalendarVersion() {
        //given
        String calendarVersion = "10";
        int rescheduleIndex = 15;
        int updateIndex = 10;
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CONFIRMED.toString());
        enrollment.setCalendarVersion(calendarVersion);
        Appointment appointment = new Appointment();
        appointment.setRescheduleIndex(rescheduleIndex);
        appointment.setUpdateIndex(updateIndex);
        //when
        when(appointmentHandler.getMaximumIndex(appointment)).thenReturn(rescheduleIndex);
        String expected = templateStatusInstaller.installTemplateType(enrollment, appointment);
        //then
        Assert.assertEquals(expected, TemplateType.UPDATE.toString());
    }

    @Test
    public void testInstallTypeWhenMaximumAppIndexLessThenCalendarVersion() {
        //given
        String calendarVersion = "15";
        int rescheduleIndex = 10;
        int updateIndex = 5;
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(EnrollmentStatus.CONFIRMED.toString());
        enrollment.setCalendarVersion(calendarVersion);
        Appointment appointment = new Appointment();
        appointment.setRescheduleIndex(rescheduleIndex);
        appointment.setUpdateIndex(updateIndex);
        //when
        when(appointmentHandler.getMaximumIndex(appointment)).thenReturn(rescheduleIndex);
        String expected = templateStatusInstaller.installTemplateType(enrollment, appointment);
        //then
        Assert.assertEquals(expected, null);
    }
}

