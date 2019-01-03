package by.iba.bussiness.sender.parser;

import by.iba.bussiness.enrollment.EnrollmentType;
import net.fortuna.ical4j.model.property.Method;
import org.springframework.stereotype.Component;

@Component
public class StatusParser {

    private static final String CANCEL_CALENDAR_METHOD = "CANCEL";

    public EnrollmentType parseCalMethodToEnrollmentStatus(Method method) {
        EnrollmentType enrollmentType;
        if (method.getValue() == CANCEL_CALENDAR_METHOD) {
            enrollmentType = EnrollmentType.CANCELLED;
        } else {
            enrollmentType = EnrollmentType.CONFIRMED;
        }
        return enrollmentType;
    }
}
