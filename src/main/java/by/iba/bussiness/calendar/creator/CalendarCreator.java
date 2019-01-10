package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.CalendarFactory;
import by.iba.bussiness.calendar.date.DateHelperDefiner;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class CalendarCreator {
    private CalendarFactory calendarFactory;
    private DateHelperDefiner dateHelperDefiner;
    private AppointmentHandler appointmentHandler;

    @Autowired
    public CalendarCreator(CalendarFactory calendarFactory,
                           DateHelperDefiner dateHelperDefiner,
                           AppointmentHandler appointmentHandler) {
        this.calendarFactory = calendarFactory;
        this.dateHelperDefiner = dateHelperDefiner;
        this.appointmentHandler = appointmentHandler;
    }

    public Calendar createCalendar(Enrollment enrollment, Appointment appointment) {
        List<TimeSlot> timeSlots = appointment.getTimeSlots();
        BigInteger meetingId = appointment.getMeetingId();
        DateHelper dateHelper = dateHelperDefiner.defineDateHelper(timeSlots, meetingId);
        String enrollmentStatus = enrollment.getStatus();
        int maximumAppointmentIndex = appointmentHandler.getMaximumIndex(appointment);

        Calendar calendar = null;
        if (enrollmentStatus.equals(EnrollmentStatus.CANCELLED)) {
            calendar = calendarFactory.createCancelCalendarTemplate(dateHelper, appointment, enrollment);
        } else {
            String enrollmentCalendarVersion = enrollment.getCalendarVersion();
            if (enrollmentCalendarVersion == null) {
                calendar = calendarFactory.createInvitationCalendarTemplate(dateHelper, appointment, enrollment);
            } else {
                int calendarVersion = Integer.parseInt(enrollment.getCalendarVersion());
                if (maximumAppointmentIndex > calendarVersion) {
                    calendar = calendarFactory.createInvitationCalendarTemplate(dateHelper, appointment, enrollment);
                }
            }
        }
        return calendar;
    }
}


