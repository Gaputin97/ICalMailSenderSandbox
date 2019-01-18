package by.iba.bussiness.enrollment;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class EnrollmentChecker {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentChecker.class);
    private EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentChecker(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    public boolean wasChangedStatus(Learner learner, BigInteger meetingId) {
        boolean wasChanged;
        String email = learner.getEmail();
        Enrollment enrollment = enrollmentService.getByEmailAndParentId(meetingId, email);
        boolean isExistLocalEnrollment = enrollment != null;
        if (isExistLocalEnrollment) {
            String enrollmentStatus = enrollment.getStatus();
            String learnerStatus = learner.getEnrollmentStatus();
            wasChanged = !enrollmentStatus.equals(learnerStatus);
            logger.info("Enrollment with meeting id " + meetingId + " and email " + email + " and enrollment type " + learnerStatus + " exists. ");
        } else {
            wasChanged = false;
        }
        return wasChanged;
    }
}

