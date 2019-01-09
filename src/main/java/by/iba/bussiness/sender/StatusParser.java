package by.iba.bussiness.sender;

import by.iba.bussiness.calendar.CalendarStatus;
import by.iba.bussiness.enrollment.EnrollmentStatus;
import net.fortuna.ical4j.model.property.Method;
import org.springframework.stereotype.Component;

@Component
public class StatusParser {
    private static final String CANCEL_CALENDAR_METHOD = "CANCEL";

    public String parseCalMethodToEnrollmentStatus(Method method) {
        String enrollmentStatus;
        if (method.getValue().equals(CANCEL_CALENDAR_METHOD)) {
            enrollmentStatus = EnrollmentStatus.CANCELLED;
        } else {
            enrollmentStatus = EnrollmentStatus.CONFIRMED;
        }
        return enrollmentStatus;
    }

    public String parseCalMethodToEnrollmentCalendarStatus(Method method) {
        String calendarStatus;
        if (method.getValue().equals(CANCEL_CALENDAR_METHOD)) {
            calendarStatus = CalendarStatus.CANCELLED;
        } else {
            calendarStatus = CalendarStatus.INVITED;
        }
        return calendarStatus;
    }
}
