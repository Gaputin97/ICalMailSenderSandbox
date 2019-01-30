package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.creator.simple.SimpleMetingCalendarTemplateCreator;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarCreator {
    private AppointmentHandler appointmentHandler;
    private SimpleMetingCalendarTemplateCreator simpleMetingCalendarTemplateCreator;

    @Autowired
    public CalendarCreator(AppointmentHandler appointmentHandler,
                           SimpleMetingCalendarTemplateCreator simpleMetingCalendarTemplateCreator) {
        this.appointmentHandler = appointmentHandler;
        this.simpleMetingCalendarTemplateCreator = simpleMetingCalendarTemplateCreator;
    }

    public Calendar createConcreteCalendarTemplate(VEvent vEvent, Enrollment enrollment, Appointment appointment) {
        String enrollmentStatus = enrollment.getStatus();
        Calendar calendar = null;
        int maximumAppointmentIndex = appointmentHandler.getMaxIndex(appointment);
        if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED.name()))) {
            calendar = simpleMetingCalendarTemplateCreator.createSimpleCancellationCalendar(vEvent);
        } else {
            String enrollmentCalendarVersion = enrollment.getCalendarVersion();
            if (enrollmentCalendarVersion == null) {
                calendar = simpleMetingCalendarTemplateCreator.createSimpleCalendarTemplate(vEvent);
            } else {
                int enrollmentCalendarVersionInt = Integer.parseInt(enrollment.getCalendarVersion());
                if (maximumAppointmentIndex > enrollmentCalendarVersionInt) {
                    calendar = simpleMetingCalendarTemplateCreator.createSimpleCalendarTemplate(vEvent);
                }
            }
        }
        return calendar;
    }
}
