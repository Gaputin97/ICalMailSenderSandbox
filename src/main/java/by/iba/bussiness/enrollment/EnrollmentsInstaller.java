package by.iba.bussiness.enrollment;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enroll.EnrollLearnerResponseStatus;
import by.iba.bussiness.enrollment.service.EnrollmentService;
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
    private AppointmentRepository appointmentRepository;

    @Autowired
    public EnrollmentsInstaller(EnrollmentService enrollmentService,
                                AppointmentRepository appointmentRepository) {
        this.enrollmentService = enrollmentService;
        this.appointmentRepository = appointmentRepository;
    }

    public List<EnrollLearnerResponseStatus> installEnrollments(List<Learner> learners, String meetingId) {
        List<EnrollLearnerResponseStatus> enrollLearnerResponseStatuses = new ArrayList<>(learners.size());
        BigInteger bigIntegerMeetingId = new BigInteger(meetingId);
        for (Learner learner : learners) {
            String email = learner.getEmail();
            String learnerEnrollmentStatus = learner.getEnrollmentStatus();
            Enrollment currentEnrollment = enrollmentService.getByEmailAndParentId(bigIntegerMeetingId, email);

            if (currentEnrollment != null) {
                String enrollmentStatus = currentEnrollment.getStatus();
                if (!enrollmentStatus.equals(learnerEnrollmentStatus)) {
                    currentEnrollment.setStatus(learnerEnrollmentStatus);
                    currentEnrollment = enrollmentService.save(currentEnrollment);
                    logger.info("Enrollment " + currentEnrollment.getUserEmail() + " has been saved with new status " + currentEnrollment.getStatus());
                    EnrollLearnerResponseStatus enrollLearnerResponseStatus =
                            new EnrollLearnerResponseStatus(true, "Enrollment status was modified.", learner.getEmail());
                    enrollLearnerResponseStatuses.add(enrollLearnerResponseStatus);
                } else {
                    EnrollLearnerResponseStatus enrollLearnerResponseStatus =
                            new EnrollLearnerResponseStatus(false, "Enrollment already exists.", learner.getEmail());
                    enrollLearnerResponseStatuses.add(enrollLearnerResponseStatus);
                }
            } else {
                Enrollment newEnrollment = new Enrollment();
                newEnrollment.setStatus(learnerEnrollmentStatus);
                newEnrollment.setParentId(bigIntegerMeetingId);
                newEnrollment.setUserEmail(email);
                enrollmentService.save(newEnrollment);
                logger.info("Enrollment " + newEnrollment.getUserEmail() + " has been saved");
                EnrollLearnerResponseStatus enrollLearnerResponseStatus =
                        new EnrollLearnerResponseStatus(true, "Enrollment was created.", learner.getEmail());
                enrollLearnerResponseStatuses.add(enrollLearnerResponseStatus);
            }

        }
        return enrollLearnerResponseStatuses;
    }

    public Enrollment installEnrollmentCalendarFields(Enrollment enrollment, int maxIndex, String calendarStatus) {
        enrollment.setCalendarStatus(calendarStatus);
        enrollment.setCalendarVersion(Integer.toString(maxIndex));
        logger.info("Enrollment " + enrollment.getUserEmail() + " has been saved with new calendar version " + enrollment.getCalendarVersion());
        return enrollment;
    }
}
