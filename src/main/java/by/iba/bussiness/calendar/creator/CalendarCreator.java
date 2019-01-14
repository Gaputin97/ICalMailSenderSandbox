package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.CalendarFactory;
import by.iba.bussiness.calendar.EnrollmentCalendarStatus;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.meeting.MeetingType;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarCreator {
    private CalendarFactory calendarFactory;
    private AppointmentHandler appointmentHandler;

    @Autowired
    public CalendarCreator(CalendarFactory calendarFactory,
                           AppointmentHandler appointmentHandler) {
        this.calendarFactory = calendarFactory;
        this.appointmentHandler = appointmentHandler;
    }

    public Calendar createCalendar(Enrollment enrollment, Appointment appointment, DateHelper dateHelper) {
        String enrollmentStatus = enrollment.getStatus();
        String enrollmentCalendarStatus = enrollment.getCalendarStatus();
        int maximumAppointmentIndex = appointmentHandler.getMaximumIndex(appointment);
        Calendar calendar = null;
        if (enrollmentStatus.equals(EnrollmentStatus.CANCELLED)) {
            if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED) &&
                    (enrollmentCalendarStatus.equals(EnrollmentCalendarStatus.INVITED) || (enrollmentCalendarStatus.equals(EnrollmentCalendarStatus.UPDATED))))) {
                if (dateHelper.getMeetingType().equals(MeetingType.COMPLEX)) {

                }
                calendar = calendarFactory.createCancelCalendarTemplate(dateHelper, appointment, enrollment);
            } else {
                String enrollmentCalendarVersion = enrollment.getCalendarVersion();
                if (enrollmentCalendarVersion == null) {
                    calendar = calendarFactory.createCalendarTemplate(dateHelper, appointment, enrollment);
                } else {
                    int calendarVersion = Integer.parseInt(enrollment.getCalendarVersion());
                    if (maximumAppointmentIndex > calendarVersion) {
                        calendar = calendarFactory.createCalendarTemplate(dateHelper, appointment, enrollment);
                    }
                }
            }

        }
        return calendar;
    }
}
