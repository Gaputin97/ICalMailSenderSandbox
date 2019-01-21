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

    public boolean wasChangedStatus(Enrollment enrollment, Learner learner) {
        boolean wasChanged;
        boolean isExistLocalEnrollment = enrollment != null;
        if (isExistLocalEnrollment) {
            String enrollmentStatus = enrollment.getStatus();
            String learnerStatus = learner.getEnrollmentStatus();
            wasChanged = !enrollmentStatus.equals(learnerStatus);
        } else {
            wasChanged = false;
        }
        return wasChanged;
    }
}

