package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.EnrollmentStatus;
import by.iba.bussiness.calendar.creator.simple.SimpleMetingCalendarTemplateCreator;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarCreator {
    private static final Logger logger = LoggerFactory.getLogger(CalendarCreator.class);
    private AppointmentHandler appointmentHandler;
    private SimpleMetingCalendarTemplateCreator simpleMetingCalendarTemplateCreator;

    @Autowired
    public CalendarCreator(AppointmentHandler appointmentHandler,
                           SimpleMetingCalendarTemplateCreator simpleMetingCalendarTemplateCreator) {
        this.appointmentHandler = appointmentHandler;
        this.simpleMetingCalendarTemplateCreator = simpleMetingCalendarTemplateCreator;
    }

    public Calendar createCalendarTemplateWithEvent(VEvent event, Enrollment enrollment, Appointment appointment) {
        String enrollmentStatus = enrollment.getStatus();
        Calendar calendar = null;
        int maximumAppointmentIndex = appointmentHandler.getMaximumIndex(appointment);
        if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED.name()))) {
            calendar = simpleMetingCalendarTemplateCreator.createSimpleCancellationCalendarWithEvent(event);
        } else {
            String enrollmentCalendarVersion = enrollment.getCalendarVersion();
            if (enrollmentCalendarVersion == null) {
                calendar = simpleMetingCalendarTemplateCreator.createSimpleCalendarTemplate(event);
            } else {
                int enrollmentCalendarVersionInt = Integer.parseInt(enrollment.getCalendarVersion());
                if (maximumAppointmentIndex > enrollmentCalendarVersionInt) {
                    calendar = simpleMetingCalendarTemplateCreator.createSimpleCalendarTemplate(event);
                }
            }
        }
        return calendar;
    }
}
