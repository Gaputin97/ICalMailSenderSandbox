package by.iba.bussiness.enrollment;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.IndexDeterminer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentUpdateChecker {
    public boolean isMustBeUpdated(String enrollmentCalendarVersion, int maxNewAppointmentIndex) {
        boolean needUpdate = false;
        if (enrollmentCalendarVersion == null) {
            needUpdate = true;
        } else {
            int enrollmentCalendarVersionInt = Integer.parseInt(enrollmentCalendarVersion);
            if (maxNewAppointmentIndex > enrollmentCalendarVersionInt) {
                needUpdate = true;
            }
        }
        return needUpdate;
    }
}
