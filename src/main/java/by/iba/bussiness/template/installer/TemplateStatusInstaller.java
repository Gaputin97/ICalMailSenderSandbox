package by.iba.bussiness.template.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.EnrollmentCalendarStatus;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.template.Template;
import org.springframework.beans.factory.annotation.Autowired;

public class TemplateStatusInstaller {

    private AppointmentHandler appointmentHandler;

    @Autowired
    public TemplateStatusInstaller(AppointmentHandler appointmentHandler) {
        this.appointmentHandler = appointmentHandler;
    }

    public void installTemplateType(Enrollment enrollment, Appointment appointment) {
        String enrollmentStatus = enrollment.getStatus();
        int maximumAppointmentIndex = appointmentHandler.getMaximumIndex(appointment);
        Template template = null;
        if (enrollmentStatus.equals(EnrollmentStatus.CANCELLED)) {
            if ((enrollmentStatus.equals(EnrollmentStatus.CANCELLED))) {
                template.setType(EnrollmentCalendarStatus.CANCELLED);
            } else {
                String enrollmentCalendarVersion = enrollment.getCalendarVersion();
                if (enrollmentCalendarVersion == null) {
                    template.setType(EnrollmentCalendarStatus.INVITED);
                } else {
                    int calendarVersion = Integer.parseInt(enrollment.getCalendarVersion());
                    if (maximumAppointmentIndex > calendarVersion) {
                        template.setType(EnrollmentCalendarStatus.UPDATED);
                    }
                }
            }
        }
    }


}
