package by.iba.bussiness.enrollment.service.v1;

import by.iba.bussiness.calendar.CalendarFactory;
import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.calendar.creator.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.date.DateHelperDefiner;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentChecker;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.invitation_template.service.InvitationTemplateService;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.response.CalendarSendingResponse;
import by.iba.bussiness.sender.MessageSender;
import by.iba.bussiness.token.model.JavaWebToken;
import by.iba.bussiness.token.service.TokenService;
import by.iba.exception.ServiceException;
import net.fortuna.ical4j.model.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

@Service
@PropertySource("endpoint.properties")
public class EnrollmentServiceImpl implements EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    private TokenService tokenService;
    private RestTemplate restTemplate;
    private MeetingService meetingService;
    private CalendarAttendeesInstaller calendarAttendeesInstaller;
    private MessageSender messageSender;
    private EnrollmentChecker enrollmentChecker;
    private InvitationTemplateService invitationTemplateService;
    private EnrollmentRepository enrollmentRepository;

    @Value("${enrollment_by_email_and_meeting_id_endpoint}")
    private String ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID_AND_EMAIL;

    @Value("${enrollment_by_parent_id_endpoint}")
    private String ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID;

    @Autowired
    public EnrollmentServiceImpl(TokenService tokenService,
                                 RestTemplate restTemplate,
                                 MeetingService meetingService,
                                 DateHelperDefiner dateHelperDefiner,
                                 CalendarFactory calendarFactory,
                                 CalendarAttendeesInstaller calendarAttendeesInstaller,
                                 MessageSender messageSender,
                                 EnrollmentChecker enrollmentChecker,
                                 InvitationTemplateService invitationTemplateService, EnrollmentRepository enrollmentRepository) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.meetingService = meetingService;
        this.calendarAttendeesInstaller = calendarAttendeesInstaller;
        this.messageSender = messageSender;
        this.enrollmentChecker = enrollmentChecker;
        this.invitationTemplateService = invitationTemplateService;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Enrollment getEnrollmentByEmailAndParentId(HttpServletRequest request, BigInteger parentId, String email) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        Enrollment enrollment = null;
        try {
            ResponseEntity<Enrollment> enrollmentResponseEntity = restTemplate.exchange(
                    ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID_AND_EMAIL + parentId + "/" + email,
                    HttpMethod.GET,
                    httpEntity,
                    Enrollment.class);
            enrollment = enrollmentResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpStatus errorStatus = e.getStatusCode();
            if (errorStatus.equals(HttpStatus.NOT_FOUND) || errorStatus.equals(HttpStatus.UNAUTHORIZED) || errorStatus.equals(HttpStatus.FORBIDDEN)) {
                logger.error("Get enrollment error from ec3 service: " + e.getStackTrace());
                throw new ServiceException(e.getMessage());
            }
        }
        return enrollment;
    }

    @Override
    public List<Enrollment> getEnrollmentByParentId(HttpServletRequest request, BigInteger parentId) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        List<Enrollment> enrollmentList = null;
        try {
            ResponseEntity<List<Enrollment>> resultEnrollmentList = restTemplate.exchange(
                    ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID + parentId,
                    HttpMethod.GET,
                    httpEntity,
                    new ParameterizedTypeReference<List<Enrollment>>() {
                    });
            enrollmentList = resultEnrollmentList.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpStatus errorStatus = e.getStatusCode();
            if (errorStatus.equals(HttpStatus.NOT_FOUND) || errorStatus.equals(HttpStatus.UNAUTHORIZED) || errorStatus.equals(HttpStatus.FORBIDDEN)) {
                logger.error("Get enrollment error from ec3 service: " + e.getStackTrace());
                throw new ServiceException(e.getMessage());
            }
        }
        return enrollmentList;
    }

    @Override
    public CalendarSendingResponse enrollUsers(HttpServletRequest request, String meetingId, List<Learner> learners) {
        CalendarSendingResponse calendarSendingResponse;
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        String invitationTemplateKey = meeting.getInvitationTemplateKey();
//        if (invitationTemplateKey.isEmpty()) {
//            logger.error("Invitation template of meeting " + meetingId + " is null");
//            throw new ServiceException("Meeting " + meetingId + " doesn't have learner template");
//        }
        Iterator<Learner> iterator = learners.iterator();
        while (iterator.hasNext()) {
            if (enrollmentChecker.isExistsEnrollment(request, iterator.next(), meeting)) {
                iterator.remove();
            }
        }
        if (learners.isEmpty()) {
            calendarSendingResponse = new CalendarSendingResponse(false, "All learners have last version of calendar");
        } else {
            Enrollment enrollment = enrollmentRepository.getOneByParentId(meeting.getId());
            String calendarUid;
            if (enrollment == null) {
                calendarUid = "";
            } else {
                calendarUid = enrollment.getCurrentCalendarUid();
            }
            List<Calendar> calendarList = calendarAttendeesInstaller.createCalendarList(learners, meeting, calendarUid);
            calendarSendingResponse = messageSender.sendMessageToAllRecipients(calendarList, meeting);
        }
        return calendarSendingResponse;
    }
}
