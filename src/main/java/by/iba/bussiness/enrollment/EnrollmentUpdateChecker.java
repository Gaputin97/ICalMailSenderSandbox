package by.iba.bussiness.enrollment;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.AppointmentIndexHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentUpdateChecker {
    private AppointmentIndexHandler appointmentIndexHandler;

    @Autowired
    public EnrollmentUpdateChecker(AppointmentIndexHandler appointmentIndexHandler) {
        this.appointmentIndexHandler = appointmentIndexHandler;
    }

    public boolean isEnrollmentMustBeUpdated(Enrollment enrollment, Appointment newAppointment) {
        boolean needUpdate = false;
        String enrollmentCalendarVersion = enrollment.getCalendarVersion();
        if (enrollmentCalendarVersion == null) {
            needUpdate = true;
        } else {
            int enrollmentCalendarVersionInt = Integer.parseInt(enrollment.getCalendarVersion());
            int maximumAppointmentIndex = appointmentIndexHandler.getMaxIndex(newAppointment);
            if (maximumAppointmentIndex > enrollmentCalendarVersionInt) {
                needUpdate = true;
            }
        }
        return needUpdate;
    }
}
