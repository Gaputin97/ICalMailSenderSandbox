package by.iba.bussiness.calendar.status;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentCalendarStatusDefiner {

    public String defineEnrollmentCalendarStatus(Enrollment enrollment) {
        String calendarStatus;
        if (enrollment.getCalendarStatus() == null) {
            calendarStatus = CalendarStatus.INVITATION.toString();
        } else {
            if (enrollment.getStatus().equals(EnrollmentStatus.CANCELLED.toString())) {
                calendarStatus = CalendarStatus.CANCELLATION.toString();
            } else {
                calendarStatus = CalendarStatus.UPDATE.toString();
            }
        }
        return calendarStatus;
    }
}
