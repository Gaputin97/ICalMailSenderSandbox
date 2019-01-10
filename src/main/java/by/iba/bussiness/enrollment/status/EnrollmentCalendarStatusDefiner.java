package by.iba.bussiness.enrollment.status;

import by.iba.bussiness.calendar.CalendarStatus;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentCalendarStatusDefiner {

    public String defineEnrollmentCalendarStatus(Enrollment enrollment) {
        String calendarStatus;
        if (enrollment.getCalendarStatus() == null) {
            calendarStatus = CalendarStatus.INVITED;
        } else {
            if (enrollment.getStatus() == EnrollmentStatus.CANCELLED) {
                calendarStatus = CalendarStatus.CANCELLED;
            } else {
                calendarStatus = CalendarStatus.UPDATED;
            }
        }
        return calendarStatus;
    }
}
