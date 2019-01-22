package by.iba.bussiness.enrollment.status;

import by.iba.bussiness.calendar.EnrollmentCalendarStatus;
import by.iba.bussiness.calendar.EnrollmentStatus;
import by.iba.bussiness.enrollment.Enrollment;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentCalendarStatusDefiner {
    public String defineEnrollmentCalendarStatus(Enrollment enrollment) {
        String calendarStatus;
        if (enrollment.getCalendarStatus() == null) {
            calendarStatus = EnrollmentCalendarStatus.INVITATION.toString();
        } else {
            if (enrollment.getStatus().equals(EnrollmentStatus.CANCELLED.toString())) {
                calendarStatus = EnrollmentCalendarStatus.CANCELLATION.toString();
            } else {
                calendarStatus = EnrollmentCalendarStatus.UPDATE.toString();
            }
        }
        return calendarStatus;
    }
}
