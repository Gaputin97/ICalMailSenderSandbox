package by.iba.bussiness.template.installer;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.template.TemplateType;
import org.springframework.stereotype.Component;

@Component
public class TemplateStatusDefiner {
    public String defineTemplateType(Enrollment enrollment, int maximumAppointmentIndex) {
        String enrollmentStatus = enrollment.getStatus();
        String templateType = null;
        if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED.name()))) {
            templateType = TemplateType.CANCELLATION.name();
        } else {
            String enrollmentCalendarVersion = enrollment.getCalendarVersion();
            if (enrollmentCalendarVersion == null) {
                templateType = TemplateType.INVITATION.name();
            } else {
                int enrollmentCalendarVersionInt = Integer.parseInt(enrollmentCalendarVersion);
                if (maximumAppointmentIndex > enrollmentCalendarVersionInt) {
                    templateType = TemplateType.UPDATE.name();
                }
            }
        }
        return templateType;
    }
}
