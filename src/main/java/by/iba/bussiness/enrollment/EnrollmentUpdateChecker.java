package by.iba.bussiness.enrollment;

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
