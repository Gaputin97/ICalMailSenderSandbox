package by.iba.bussiness.appointment;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppointmentInstallerTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private AppointmentHandler appointmentHandler;
    @Mock
    private AppointmentCreator appointmentCreator;

    @InjectMocks
    private AppointmentInstaller appointmentInstaller;

    @Test
    public void testAppointInstallWithOutOldAppointment() {
        Appointment newAppointment = new Appointment();
        Meeting meeting = new Meeting();
        InvitationTemplate invitationTemplate = new InvitationTemplate();

        when(appointmentCreator.createAppointment(meeting, invitationTemplate)).thenReturn(newAppointment);
        when(appointmentRepository.save(newAppointment)).thenReturn(newAppointment);

        Appointment actualAppointment = appointmentInstaller.installAppointment(meeting, invitationTemplate, eq(null));

        Assert.assertEquals(actualAppointment, newAppointment);
    }

    @Test
    public void testAppointInstallWithZeroIndexes() {
        Appointment oldAppointment = new Appointment();
        Appointment newAppointment = mock(Appointment.class);
        Meeting meeting = new Meeting();
        InvitationTemplate invitationTemplate = new InvitationTemplate();
        int updateIndex = 0;
        int rescheduleIndex = 0;

        when(appointmentHandler.updateAppointment(meeting, invitationTemplate)).thenReturn(newAppointment);
        when(newAppointment.getUpdateIndex()).thenReturn(updateIndex);
        when(newAppointment.getRescheduleIndex()).thenReturn(rescheduleIndex);
        when(appointmentRepository.save(newAppointment)).thenReturn(newAppointment);

        Appointment actualAppointment = appointmentInstaller.installAppointment(meeting, invitationTemplate, oldAppointment);

        Assert.assertEquals(newAppointment, actualAppointment);
    }

    @Test
    public void testAppointInstallWhenUpdatedRescheduleIndexMoreThanOldRescheduleIndex() {
        Appointment oldAppointment = mock(Appointment.class);
        oldAppointment.setRescheduleIndex(0);
        Appointment updatedAppointment = mock(Appointment.class);
        updatedAppointment.setRescheduleIndex(1);
        Meeting meeting = new Meeting();
        InvitationTemplate invitationTemplate = new InvitationTemplate();

        int updatedRescheduleIndex = 1;
        int oldUpdateIndex = 0;

        Appointment newAppointment = new Appointment();

        when(appointmentHandler.updateAppointment(meeting, invitationTemplate)).thenReturn(updatedAppointment);
        when(updatedAppointment.getRescheduleIndex()).thenReturn(updatedRescheduleIndex);
        when(oldAppointment.getRescheduleIndex()).thenReturn(oldUpdateIndex);
        when(appointmentRepository.save(newAppointment)).thenReturn(updatedAppointment);

        newAppointment = appointmentInstaller.installAppointment(eq(meeting), eq(invitationTemplate), eq(oldAppointment));

        Assert.assertEquals(newAppointment, updatedAppointment);
    }
}