package by.iba.bussiness.enrollment;

import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class EnrollmentChecker {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentChecker.class);
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentChecker(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public boolean wasChangedStatus(Learner learner, BigInteger meetingId) {
        boolean wasChanged;
        String email = learner.getEmail();
        Enrollment enrollment = enrollmentRepository.getByEmailAndParentId(meetingId, email);
        boolean isExistLocalEnrollment = enrollment != null;
        if (isExistLocalEnrollment) {
            String enrollmentStatus = enrollment.getStatus();
            String learnerStatus = learner.getEnrollmentStatus();
            if (enrollmentStatus != learnerStatus) {
                wasChanged = true;
            } else {
                wasChanged = false;
            }
            logger.info("Enrollment with meeting id " + meetingId + " and email " + email + " and enrollment type " + enrollmentStatus + " exists. ");
        } else {
            wasChanged = false;
        }
        return wasChanged;
    }
}

