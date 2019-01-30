package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentInstallerTest {
    @Mock
    private AppointmentHandler appointmentHandler;
    @Mock
    private AppointmentCreator appointmentCreator;
    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentInstaller appointmentInstaller;

    @Test
    public void testAppointInstallWithoutOldAppointment() {
        Appointment expectedAppointment = new Appointment();
        Meeting meeting = new Meeting();
        InvitationTemplate invitationTemplate = new InvitationTemplate();

        when(appointmentCreator.createAppointment(meeting, invitationTemplate)).thenReturn(expectedAppointment);

        Appointment actualAppointment = appointmentInstaller.installAppointment(meeting, invitationTemplate, null);

        assertEquals(expectedAppointment, actualAppointment);
    }

    @Test
    public void testAppointInstallWithZeroIndexes() {
        Appointment oldAppointment = new Appointment();
        Appointment expectedAppointment = mock(Appointment.class);
        Meeting meeting = mock(Meeting.class);
        InvitationTemplate invitationTemplate = mock(InvitationTemplate.class);

        when(appointmentHandler.updateAppointment(meeting, invitationTemplate)).thenReturn(expectedAppointment);
        when(expectedAppointment.getUpdateIndex()).thenReturn(3);

        Appointment actualAppointment = appointmentInstaller.installAppointment(meeting, invitationTemplate, oldAppointment);

        assertEquals(expectedAppointment, actualAppointment);
    }

    @Test
    public void testAppointInstallWhenUpdatedRescheduleIndexMoreThanOldRescheduleIndex() {
        Appointment oldAppointment = mock(Appointment.class);
        Appointment updatedAppointment = mock(Appointment.class);
        Appointment newAppointment = new Appointment();
        Meeting meeting = new Meeting();
        InvitationTemplate invitationTemplate = new InvitationTemplate();

        int updatedRescheduleIndex = 1;
        int oldRescheduledIndex = 0;

        when(appointmentHandler.updateAppointment(meeting, invitationTemplate)).thenReturn(updatedAppointment);
        when(updatedAppointment.getRescheduleIndex()).thenReturn(updatedRescheduleIndex);
        when(oldAppointment.getRescheduleIndex()).thenReturn(oldRescheduledIndex);
        when(appointmentRepository.save(newAppointment)).thenReturn(newAppointment);

        newAppointment = appointmentInstaller.installAppointment(meeting, invitationTemplate, oldAppointment);
        assertEquals(newAppointment, updatedAppointment);
    }

    @Test
    public void testAppointInstallWhenUpdatedUpdateIndexMoreThanOldUpdateIndex() {
        Appointment oldAppointment = mock(Appointment.class);
        Appointment updatedAppointment = mock(Appointment.class);
        Appointment newAppointment = new Appointment();
        Meeting meeting = new Meeting();
        InvitationTemplate invitationTemplate = new InvitationTemplate();

        int updatedUpdateIndex = 4;
        int oldUpdateIndex = 2;

        when(appointmentHandler.updateAppointment(meeting, invitationTemplate)).thenReturn(updatedAppointment);
        when(updatedAppointment.getRescheduleIndex()).thenReturn(updatedUpdateIndex);
        when(oldAppointment.getRescheduleIndex()).thenReturn(oldUpdateIndex);
        when(appointmentRepository.save(newAppointment)).thenReturn(newAppointment);

        newAppointment = appointmentInstaller.installAppointment(meeting, invitationTemplate, oldAppointment);

        assertEquals(newAppointment, updatedAppointment);
    }

    @Test
    public void testAppointInstallWhenOldUpdateIndexMoreThanUpdated() {
        Appointment oldAppointment = mock(Appointment.class);
        Appointment updatedAppointment = mock(Appointment.class);
        Meeting meeting = new Meeting();
        InvitationTemplate invitationTemplate = new InvitationTemplate();

        int updatedUpdateIndex = 3;
        int oldUpdateIndex = 4;

        when(appointmentHandler.updateAppointment(meeting, invitationTemplate)).thenReturn(updatedAppointment);
        when(updatedAppointment.getUpdateIndex()).thenReturn(updatedUpdateIndex);
        when(oldAppointment.getUpdateIndex()).thenReturn(oldUpdateIndex);

        Appointment newAppointment;
        newAppointment = appointmentInstaller.installAppointment(meeting, invitationTemplate, oldAppointment);

        assertEquals(newAppointment, oldAppointment);
    }

}