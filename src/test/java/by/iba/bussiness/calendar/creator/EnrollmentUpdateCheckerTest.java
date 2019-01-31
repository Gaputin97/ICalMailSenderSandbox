package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.AppointmentIndexHandler;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentUpdateChecker;
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
public class EnrollmentUpdateCheckerTest {

    @Mock
    private AppointmentIndexHandler appointmentIndexHandler;
    @InjectMocks
    private EnrollmentUpdateChecker calendarCreator;

}