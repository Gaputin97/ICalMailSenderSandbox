package by.iba.bussiness.enrollment.status;

import by.iba.bussiness.enrollment.Enrollment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentStatusChecker {
    public boolean isAnyEnrollmentHasConfirmedStatus(List<Enrollment> enrollments) {
        return enrollments.stream().map(Enrollment::getStatus).anyMatch(EnrollmentStatus.CONFIRMED.name()::equals);
    }

    public boolean isAnyEnrollmentHasCancelledStatus(List<Enrollment> enrollments) {
        return enrollments.stream().map(Enrollment::getStatus).anyMatch(EnrollmentStatus.CANCELLED.name()::equals);
    }
}

