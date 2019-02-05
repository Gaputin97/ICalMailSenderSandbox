package by.iba.bussiness.calendar.learner;

import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LearnerStatusChecker {

    public boolean isAnyLearnerHasCancelledStatus(List<Learner> learnerList) {
        return learnerList.stream().map(Learner::getEnrollmentStatus).anyMatch(EnrollmentStatus.CANCELLED.name()::equals);
    }
}
