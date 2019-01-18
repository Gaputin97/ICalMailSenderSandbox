package by.iba.bussiness.enrollment.service.v1;

import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.meeting.MeetingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment getByEmailAndParentId(BigInteger parentId, String userEmail) {
        return enrollmentRepository.getByEmailAndParentId(parentId, userEmail);
    }

<<<<<<< HEAD
    @Override
    public Enrollment getByEmailAndParentIdAndType(BigInteger parentId, String userEmail, String enrollmentStatus) {
        return enrollmentRepository.getByEmailAndParentIdAndType(parentId, userEmail, enrollmentStatus);
    }

    @Override
    public List<Enrollment> getAllByParentId(BigInteger parentId) {
        return enrollmentRepository.getAllByParentId(parentId);
=======
        MeetingType newMeetingType = meetingTypeDefiner.defineMeetingType(appointment.getSessionList());
        List<Session> sessions = null;
        MeetingType oldMeetingType = null;
        if (oldAppointment != null) {
            oldMeetingType = meetingTypeDefiner.defineMeetingType(oldAppointment.getSessionList());
        }
        if (newMeetingType.equals(MeetingType.SIMPLE)) {
            Rrule rrule = rruleDefiner.defineRrule(sessions);
            mailSendingResponseStatusList = simpleCalendarSenderFacade.sendCalendar(rrule, appointment);
        } else {
            mailSendingResponseStatusList = complexTemplateSenderFacade.sendTemplate(appointment, oldMeetingType);
        }
        return mailSendingResponseStatusList;
>>>>>>> 093547b095dd9035df6e032ab57eae0287b79228
    }
}