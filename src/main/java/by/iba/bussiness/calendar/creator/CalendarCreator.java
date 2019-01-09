package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.CalendarFactory;
import by.iba.bussiness.calendar.date.DateHelperDefiner;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentStatus;
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

    @Autowired
    public CalendarCreator(CalendarFactory calendarFactory,
                           DateHelperDefiner dateHelperDefiner) {
        this.calendarFactory = calendarFactory;
        this.dateHelperDefiner = dateHelperDefiner;
    }

    public Calendar createCalendar(Enrollment enrollment, Appointment appointment) {
        Calendar calendar;
        List<TimeSlot> timeSlots = appointment.getTimeSlots();
        BigInteger meetingId = appointment.getMeetingId();
        DateHelper dateHelper = dateHelperDefiner.defineDateHelper(timeSlots, meetingId);
        String enrollmentStatus = enrollment.getStatus();
        if (enrollmentStatus.equals(EnrollmentStatus.CANCELLED)) {
            Calendar calendarCancel = calendarFactory.createCancelCalendarTemplate(dateHelper, appointment, enrollment);
            calendar = calendarCancel;
        } else {
            Calendar calendarInvite = calendarFactory.createInvitationCalendarTemplate(dateHelper, appointment, enrollment);
            calendar = calendarInvite;
        }
        return calendar;
    }

}