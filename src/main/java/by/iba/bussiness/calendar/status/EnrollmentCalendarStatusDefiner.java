package by.iba.bussiness.calendar.status;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
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
