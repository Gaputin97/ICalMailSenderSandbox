package by.iba.bussiness.enrollment;

import by.iba.bussiness.enrollment.service.v1.EnrollmentServiceImpl;
import by.iba.bussiness.meeting.Meeting;
import by.iba.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

@Component
public class EnrollmentChecker {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    private EnrollmentServiceImpl enrollmentService;

    @Autowired
    public EnrollmentChecker(EnrollmentServiceImpl enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    public void isExistsEnrollment(HttpServletRequest request, List<String> emails, Meeting meeting) {
        BigInteger meetingId = meeting.getId();
        for (String email : emails) {
            boolean isExistThirdPartyEnrollment = enrollmentService.getEnrollmentByEmailAndMeetingId(request, meetingId, email) != null;
            boolean isExistLocalEnrollment = enrollmentService.getLocalEnrollmentByEmailAndMeetingId(meetingId, email) != null;
            if (isExistLocalEnrollment || isExistThirdPartyEnrollment) {
                logger.error("Enrollment with meeting id " + meetingId + " and email " + email + " exists. ");
                throw new ServiceException("Meeting " + meeting.getSummary() + " was sended to " + email);
            }
        }
    }
}

