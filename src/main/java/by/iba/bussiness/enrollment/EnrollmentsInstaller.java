package by.iba.bussiness.enrollment;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enroll.EnrollLearnerStatus;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.calendar.status.EnrollmentCalendarStatusDefiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Component
public class EnrollmentsInstaller {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentsInstaller.class);
    private EnrollmentService enrollmentService;
    private EnrollmentChecker enrollmentChecker;
    private EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner;
    private AppointmentHandler appointmentHandler;

    @Autowired
    public EnrollmentsInstaller(EnrollmentService enrollmentService,
                                EnrollmentChecker enrollmentChecker,
                                EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner,
                                AppointmentHandler appointmentHandler) {
        this.enrollmentService = enrollmentService;
        this.enrollmentChecker = enrollmentChecker;
        this.enrollmentCalendarStatusDefiner = enrollmentCalendarStatusDefiner;
        this.appointmentHandler = appointmentHandler;
    }

    public List<EnrollLearnerStatus> installEnrollmentsByLearners(List<Learner> learners, String meetingId) {
        List<EnrollLearnerStatus> enrollLearnerStatuses = new ArrayList<>(learners.size());
        BigInteger bigIntegerMeetingId = new BigInteger(meetingId);
        for (Learner learner : learners) {
            String email = learner.getEmail();
            String enrollmentStatus = learner.getEnrollmentStatus();
            Enrollment oldEnrollment = enrollmentService.getByEmailAndParentIdAndType(bigIntegerMeetingId, email, enrollmentStatus);
            if (oldEnrollment == null) {
                oldEnrollment = enrollmentService.getByEmailAndParentId(bigIntegerMeetingId, email);
                if (enrollmentChecker.wasChangedStatus(oldEnrollment, learner)) {
                    oldEnrollment.setStatus(enrollmentStatus);
                    enrollmentService.save(oldEnrollment);
                    logger.info("Enrollment " + oldEnrollment.getUserEmail() + " has been saved with new status " + oldEnrollment.getStatus());
                    EnrollLearnerStatus enrollLearnerStatus =
                            new EnrollLearnerStatus(true, "Enrollment was modified.", learner.getEmail());
                    enrollLearnerStatuses.add(enrollLearnerStatus);
                } else {
                    Enrollment newEnrollment = new Enrollment();
                    newEnrollment.setStatus(enrollmentStatus);
                    newEnrollment.setParentId(bigIntegerMeetingId);
                    newEnrollment.setUserEmail(email);
                    enrollmentService.save(newEnrollment);
                    logger.info("Enrollment " + newEnrollment.getUserEmail() + " has been saved");
                    EnrollLearnerStatus enrollLearnerStatus =
                            new EnrollLearnerStatus(true, "Enrollment was created.", learner.getEmail());
                    enrollLearnerStatuses.add(enrollLearnerStatus);
                }
            } else {
                EnrollLearnerStatus enrollLearnerStatus =
                        new EnrollLearnerStatus(false, "Enrollment already exists.", learner.getEmail());
                enrollLearnerStatuses.add(enrollLearnerStatus);
            }
        }
        return enrollLearnerStatuses;
    }

    public void installEnrollmentCalendarFields(Enrollment enrollment, Appointment appointment) {
        int maximumIndex = appointmentHandler.getMaximumIndex(appointment);
        String calendarStatus = enrollmentCalendarStatusDefiner.defineEnrollmentCalendarStatus(enrollment);
        enrollment.setCalendarStatus(calendarStatus);
        enrollment.setCalendarVersion(Integer.toString(maximumIndex));
        enrollmentService.save(enrollment);
        logger.info("Enrollment " + enrollment.getUserEmail() + " has been saved with new calendar version " + enrollment.getCalendarVersion());
    }
}
