package by.iba.bussiness.enrollment;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.status.EnrollmentCalendarStatusDefiner;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Component
public class EnrollmentsInstaller {
    private EnrollmentRepository enrollmentRepository;
    private EnrollmentChecker enrollmentChecker;
    private EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner;
    private AppointmentHandler appointmentHandler;

    @Autowired
    public EnrollmentsInstaller(EnrollmentRepository enrollmentRepository,
                                EnrollmentChecker enrollmentChecker,
                                EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner,
                                AppointmentHandler appointmentHandler) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentChecker = enrollmentChecker;
        this.enrollmentCalendarStatusDefiner = enrollmentCalendarStatusDefiner;
        this.appointmentHandler = appointmentHandler;
    }

    public void installEnrollmentCommonFields(List<Learner> learners, String meetingId) {
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

    public void installEnrollmentCalendarFields(Enrollment enrollment, Appointment appointment) {
        Integer maximumIndex = appointmentHandler.getMaximumIndex(appointment);
        String calendarStatus = enrollmentCalendarStatusDefiner.defineEnrollmentCalendarStatus(enrollment);
        enrollment.setCalendarStatus(calendarStatus);
        enrollment.setCalendarVersion(maximumIndex.toString());
        enrollmentRepository.save(enrollment);
    }
}
