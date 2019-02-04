package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.IndexDeterminer;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.template.TemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemplateStatusInstaller {
    private IndexDeterminer indexDeterminer;

    @Autowired
    public TemplateStatusInstaller(IndexDeterminer indexDeterminer) {
        this.indexDeterminer = indexDeterminer;
    }

    public String installTemplateType(Enrollment enrollment, Appointment appointment) {
        String enrollmentStatus = enrollment.getStatus();
        String templateType = null;
        int maximumAppointmentIndex = indexDeterminer.getMaxIndex(appointment);
        if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED.toString()))) {
            templateType = TemplateType.CANCELLATION.toString();
        } else {
            String enrollmentCalendarVersion = enrollment.getCalendarVersion();
            if (enrollmentCalendarVersion == null) {
                templateType = TemplateType.INVITATION.toString();
            } else {
                int calendarVersion = Integer.parseInt(enrollmentCalendarVersion);
                if (maximumAppointmentIndex > calendarVersion) {
                    templateType = TemplateType.UPDATE.toString();
                }
            }
        }
        return templateType;
    }
}
