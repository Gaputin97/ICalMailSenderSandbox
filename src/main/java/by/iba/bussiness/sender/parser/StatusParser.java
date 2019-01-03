package by.iba.bussiness.sender.parser;

import by.iba.bussiness.enrollment.EnrollmentType;
import net.fortuna.ical4j.model.property.Method;
import org.springframework.stereotype.Component;

@Component
public class StatusParser {

    public EnrollmentType parseCalMethodToEnrollmentStatus(Method method) {
        EnrollmentType enrollmentType;
        if (method == Method.CANCEL) {
            enrollmentType = EnrollmentType.CANCELLED;
        } else {
            enrollmentType = EnrollmentType.CONFIRMED;
        }
        return enrollmentType;
    }
}
