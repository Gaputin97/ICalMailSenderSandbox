package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.EnrollmentStatus;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.template.Template;
import by.iba.bussiness.template.TemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemplateStatusInstaller {

    private AppointmentHandler appointmentHandler;

    @Autowired
    public TemplateStatusInstaller(AppointmentHandler appointmentHandler) {
        this.appointmentHandler = appointmentHandler;
    }

    public void installTemplateType(Enrollment enrollment, Appointment appointment, Template template) {
        String enrollmentStatus = enrollment.getStatus();
        int maximumAppointmentIndex = appointmentHandler.getMaximumIndex(appointment);
        if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED))) {
            template.setType(TemplateType.CANCELLATION.toString());
        } else {
            String enrollmentCalendarVersion = enrollment.getCalendarVersion();
            if (enrollmentCalendarVersion == null) {
                template.setType(TemplateType.INVITATION.toString());
            } else {
                int calendarVersion = Integer.parseInt(enrollment.getCalendarVersion());
                if (maximumAppointmentIndex > calendarVersion) {
                    template.setType(TemplateType.UPDATE.toString());
                }
            }
        }
    }


}
