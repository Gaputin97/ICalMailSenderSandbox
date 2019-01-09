package by.iba.bussiness.enrollment.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentHandler;
import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentChecker;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.sender.StatusParser;
import io.swagger.models.auth.In;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Component
public class EnrollmentsInstaller {
    private EnrollmentRepository enrollmentRepository;
    private EnrollmentChecker enrollmentChecker;
    private StatusParser statusParser;
    private AppointmentHandler appointmentHandler;

    @Autowired
    public EnrollmentsInstaller(EnrollmentRepository enrollmentRepository,
                                EnrollmentChecker enrollmentChecker,
                                StatusParser statusParser,
                                AppointmentHandler appointmentHandler) {
        this.enrollmentRepository = enrollmentRepository;
        this.enrollmentChecker = enrollmentChecker;
        this.statusParser = statusParser;
        this.appointmentHandler = appointmentHandler;
    }

    public void install(List<Learner> learners, String meetingId) {
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

    public void installCalendarFields(Enrollment enrollment, Calendar calendar, Appointment appointment) {
        Integer maximumIndex = appointmentHandler.getMaximumIndex(appointment);
        Method method = calendar.getMethod();
        enrollment.setCalendarStatus(statusParser.parseCalMethodToEnrollmentCalendarStatus(method));
        enrollment.setCalendarVersion(maximumIndex.toString());
        enrollmentRepository.save(enrollment);
    }
}
