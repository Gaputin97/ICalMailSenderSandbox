package by.iba.bussiness.enrollment;

import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.service.EnrollmentService;
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
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentChecker.class);
    private EnrollmentService enrollmentService;
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentChecker(EnrollmentService enrollmentService, EnrollmentRepository enrollmentRepository) {
        this.enrollmentService = enrollmentService;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void isExistsEnrollment(HttpServletRequest request, List<String> emails, Meeting meeting) {
        BigInteger meetingId = meeting.getId();
        for (String email : emails) {
            boolean isExistThirdPartyEnrollment = enrollmentService.getEnrollmentByEmailAndParentId(request, meetingId, email) != null;
            boolean isExistLocalEnrollment = enrollmentRepository.getByEmailAndParentId(meetingId, email) != null;
            if (isExistLocalEnrollment || isExistThirdPartyEnrollment) {
                logger.error("Enrollment with meeting id " + meetingId + " and email " + email + " exists. ");
                throw new ServiceException("Meeting " + meeting.getSummary() + " was sended to " + email);
            }
        }
    }
}

