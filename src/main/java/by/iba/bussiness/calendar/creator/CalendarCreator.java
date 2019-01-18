package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.EnrollmentStatus;
import by.iba.bussiness.calendar.creator.recurrence.RecurrenceMeetingCalendarTemplateCreator;
import by.iba.bussiness.enrollment.Enrollment;
import net.fortuna.ical4j.model.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

@Component
public class CalendarCreator {
    private static final Logger logger = LoggerFactory.getLogger(CalendarCreator.class);
    private AppointmentHandler appointmentHandler;
    private RecurrenceMeetingCalendarTemplateCreator recurrenceMeetingCalendarTemplateCreator;

    @Autowired
    public CalendarCreator(AppointmentHandler appointmentHandler,
                           RecurrenceMeetingCalendarTemplateCreator recurrenceMeetingCalendarTemplateCreator) {
        this.appointmentHandler = appointmentHandler;
        this.recurrenceMeetingCalendarTemplateCreator = recurrenceMeetingCalendarTemplateCreator;
    }

    public Calendar createCalendar(Calendar calendar, Enrollment enrollment, Appointment appointment) {
        String enrollmentStatus = enrollment.getStatus();
        int maximumAppointmentIndex = appointmentHandler.getMaximumIndex(appointment);
        Calendar newCalendar = null;
        try {
            newCalendar = new Calendar(calendar);
        } catch (ParseException | IOException | URISyntaxException e) {
            logger.error("Error cause with parse calendar data:", e);
        }
        if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED))) {
            recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarCancellationTemplate(newCalendar);
        } else {
            String enrollmentCalendarVersion = enrollment.getCalendarVersion();
            if (enrollmentCalendarVersion == null) {
                recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarInvitationTemplate(newCalendar);
            } else {
                int calendarVersion = Integer.parseInt(enrollment.getCalendarVersion());
                if (maximumAppointmentIndex > calendarVersion) {
                    recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarInvitationTemplate(newCalendar);
                }
            }
        }
        return newCalendar;
    }
}
