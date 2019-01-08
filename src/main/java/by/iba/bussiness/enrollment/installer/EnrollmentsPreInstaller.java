package by.iba.bussiness.enrollment.installer;

import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentChecker;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Component
public class EnrollmentsPreInstaller {
    private EnrollmentRepository enrollmentRepository;
    private EnrollmentChecker enrollmentChecker;

    @Autowired
    public EnrollmentsPreInstaller(EnrollmentRepository enrollmentRepository,
                                   EnrollmentChecker enrollmentChecker) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentChecker = enrollmentChecker;
    }

    public void installEnrollments(List<Learner> learners, String meetingId) {
        BigInteger bigIntegerMeetingId = new BigInteger(meetingId);
        for (Learner learner : learners) {
            String email = learner.getEmail();
            String enrollmentStatus = learner.getEnrollmentStatus();
            Enrollment oldEnrollment = enrollmentRepository.getByEmailAndParentIdAndType(bigIntegerMeetingId, email, enrollmentStatus);
            if (oldEnrollment == null) {
                if (enrollmentChecker.wasChangedStatus(learner, bigIntegerMeetingId)) {
                    oldEnrollment = enrollmentRepository.getByEmailAndParentId(bigIntegerMeetingId, email);
                    oldEnrollment.setStatus(enrollmentStatus);
                    enrollmentRepository.save(oldEnrollment);
                } else {
                    Enrollment newEnrollment = new Enrollment();
                    newEnrollment.setStatus(enrollmentStatus);
                    newEnrollment.setParentId(bigIntegerMeetingId);
                    newEnrollment.setUserEmail(email);
                    newEnrollment.setCurrentCalendarUid(UUID.randomUUID().toString());
                    enrollmentRepository.save(newEnrollment);


                }
            }
        }
    }
}
