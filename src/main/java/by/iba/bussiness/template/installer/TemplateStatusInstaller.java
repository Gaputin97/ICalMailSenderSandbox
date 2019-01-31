package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.AppointmentIndexHandler;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.template.TemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemplateStatusInstaller {

    private AppointmentIndexHandler appointmentIndexHandler;

    @Autowired
    public TemplateStatusInstaller(AppointmentIndexHandler appointmentIndexHandler) {
        this.appointmentIndexHandler = appointmentIndexHandler;
    }

    public String installTemplateType(Enrollment enrollment, Appointment appointment) {
        String enrollmentStatus = enrollment.getStatus();
        String templateType = null;
        int maximumAppointmentIndex = appointmentIndexHandler.getMaxIndex(appointment);
        if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED.toString()))) {
            templateType = TemplateType.CANCELLATION.toString();
        } else {
            String enrollmentCalendarVersion = enrollment.getCalendarVersion();
            if (enrollmentCalendarVersion == null) {
                templateType = TemplateType.INVITATION.toString();
            } else {
                int calendarVersion = Integer.parseInt(enrollment.getCalendarVersion());
                if (maximumAppointmentIndex > calendarVersion) {
                    templateType = TemplateType.UPDATE.toString();
                }
            }
        }
        return templateType;
    }
}
