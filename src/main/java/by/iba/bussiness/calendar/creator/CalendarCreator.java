package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.EnrollmentStatus;
import by.iba.bussiness.calendar.creator.recurrence.RecurrenceMeetingCalendarTemplateCreator;
import by.iba.bussiness.enrollment.Enrollment;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarCreator {
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
        if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED))) {
            recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarCancellationTemplate(calendar);
        } else {
            String enrollmentCalendarVersion = enrollment.getCalendarVersion();
            if (enrollmentCalendarVersion == null) {
                recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarInvitationTemplate(calendar);
            } else {
                int calendarVersion = Integer.parseInt(enrollment.getCalendarVersion());
                if (maximumAppointmentIndex > calendarVersion) {
                    recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarInvitationTemplate(calendar);
                }
            }
        }
        return calendar;
    }
}
