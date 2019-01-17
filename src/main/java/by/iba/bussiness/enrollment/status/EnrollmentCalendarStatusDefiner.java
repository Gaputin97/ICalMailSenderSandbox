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
            calendarStatus = EnrollmentCalendarStatus.INVITED.toString();
        } else {
            if (enrollment.getStatus().equals(EnrollmentStatus.CANCELLED)) {
                calendarStatus = EnrollmentCalendarStatus.CANCELLED.toString();
            } else {
                calendarStatus = EnrollmentCalendarStatus.UPDATED.toString();
            }
        }
        return calendarStatus;
    }
}
