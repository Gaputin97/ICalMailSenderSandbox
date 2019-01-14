package by.iba.bussiness.enrollment.status;

import by.iba.bussiness.calendar.EnrollmentCalendarStatus;
import by.iba.bussiness.enrollment.Enrollment;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentCalendarStatusDefiner {
    public String defineEnrollmentCalendarStatus(Enrollment enrollment) {
        String calendarStatus;
        if (enrollment.getCalendarStatus() == null) {
            calendarStatus = EnrollmentCalendarStatus.INVITED;
        } else {
            if (enrollment.getStatus().equals(EnrollmentStatus.CANCELLED)) {
                calendarStatus = EnrollmentCalendarStatus.CANCELLED;
            } else {
                calendarStatus = EnrollmentCalendarStatus.UPDATED;
            }
        }
        return calendarStatus;
    }
}
