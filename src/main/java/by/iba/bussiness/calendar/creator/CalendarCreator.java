package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.AppointmentIndexHandler;
import by.iba.bussiness.calendar.creator.simple.SimpleMeetingCalendarTemplateCreator;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarCreator {
    private AppointmentIndexHandler appointmentIndexHandler;
    private SimpleMeetingCalendarTemplateCreator simpleMeetingCalendarTemplateCreator;

    @Autowired
    public CalendarCreator(AppointmentIndexHandler appointmentIndexHandler,
                           SimpleMeetingCalendarTemplateCreator simpleMeetingCalendarTemplateCreator) {
        this.appointmentIndexHandler = appointmentIndexHandler;
        this.simpleMeetingCalendarTemplateCreator = simpleMeetingCalendarTemplateCreator;
    }

    public Calendar createConcreteCalendarTemplate(VEvent vEvent, Enrollment enrollment, Appointment newAppointment) {
        String enrollmentStatus = enrollment.getStatus();
        Calendar calendar = null;
        if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED.name()))) {
            calendar = simpleMeetingCalendarTemplateCreator.createSimpleCancellationCalendar(vEvent);
        } else {
            String enrollmentCalendarVersion = enrollment.getCalendarVersion();
            if (enrollmentCalendarVersion == null) {
                calendar = simpleMeetingCalendarTemplateCreator.createSimpleCalendarTemplate(vEvent);
            } else {
                int enrollmentCalendarVersionInt = Integer.parseInt(enrollment.getCalendarVersion());
                int maximumAppointmentIndex = appointmentIndexHandler.getMaxIndex(newAppointment);
                if (maximumAppointmentIndex > enrollmentCalendarVersionInt) {
                    calendar = simpleMeetingCalendarTemplateCreator.createSimpleCalendarTemplate(vEvent);
                }
            }
        }
        return calendar;
    }
}
