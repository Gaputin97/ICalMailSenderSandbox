package by.iba.bussiness.enrollment.creator;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.sender.StatusParser;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Method;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

@org.springframework.stereotype.Component
public class CalendarEnrollmentCreator {
    private EnrollmentRepository enrollmentRepository;
    private StatusParser statusParser;

    @Autowired
    public CalendarEnrollmentCreator(EnrollmentRepository enrollmentRepository, StatusParser statusParser) {
        this.enrollmentRepository = enrollmentRepository;
        this.statusParser = statusParser;
    }

    public Enrollment createCalendarEnrollment(Calendar calendar, BigInteger meetingId, String userEmail) {
        Method method = calendar.getMethod();
        VEvent event = (VEvent) calendar.getComponents().getComponent(Component.VEVENT);
        Enrollment enrollment = enrollmentRepository.getByEmailAndParentId(meetingId, userEmail);
        if (enrollment == null) {
            enrollment = new Enrollment();
        }
        enrollment.setParentId(meetingId);
        enrollment.setUserEmail(userEmail);
        String enrollmentStatus = statusParser.parseCalMethodToEnrollmentStatus(method);
        enrollment.setStatus(enrollmentStatus);
        enrollment.setCurrentCalendarUid(event.getUid().getValue());
        enrollment.setCalendarVersion(event.getSequence().getValue());
        return enrollment;
    }
}