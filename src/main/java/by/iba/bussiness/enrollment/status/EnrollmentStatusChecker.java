package by.iba.bussiness.enrollment.status;

import by.iba.bussiness.enrollment.Enrollment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentStatusChecker {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentStatusChecker.class);

    public boolean areAllEnrollmentsHasCancelledStatus(List<Enrollment> enrollments) {
        return enrollments.stream().map(Enrollment::getStatus).allMatch(EnrollmentStatus.CANCELLED.name()::equals);
    }

    public boolean areAllEnrollmentsHasConfirmedStatus(List<Enrollment> enrollments) {
        return enrollments.stream().map(Enrollment::getStatus).allMatch(EnrollmentStatus.CONFIRMED.name()::equals);
    }
}

