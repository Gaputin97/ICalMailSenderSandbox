package by.iba.bussiness.enrollment;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.IndexDeterminer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentUpdateChecker {
    private IndexDeterminer indexDeterminer;

    @Autowired
    public EnrollmentUpdateChecker(IndexDeterminer indexDeterminer) {
        this.indexDeterminer = indexDeterminer;
    }

    public boolean isEnrollmentMustBeUpdated(Enrollment enrollment, Appointment newAppointment) {
        boolean needUpdate = false;
        String enrollmentCalendarVersion = enrollment.getCalendarVersion();
        if (enrollmentCalendarVersion == null) {
            needUpdate = true;
        } else {
            int enrollmentCalendarVersionInt = Integer.parseInt(enrollment.getCalendarVersion());
            int maximumAppointmentIndex = indexDeterminer.getMaxIndex(newAppointment);
            if (maximumAppointmentIndex > enrollmentCalendarVersionInt) {
                needUpdate = true;
            }
        }
        return needUpdate;
    }
}
