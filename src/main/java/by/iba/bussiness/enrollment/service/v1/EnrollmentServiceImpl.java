package by.iba.bussiness.enrollment.service.v1;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentInstaller;
import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.calendar.creator.CalendarCreator;
import by.iba.bussiness.calendar.creator.installer.CalendarAttendeesInstaller;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.installer.EnrollmentsInstaller;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.invitation_template.service.InvitationTemplateService;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.sender.MessageSender;
import by.iba.bussiness.sender.ResponseStatus;
import by.iba.bussiness.sender.StatusParser;
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
import java.util.ArrayList;
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
    private InvitationTemplateService invitationTemplateService;
    private EnrollmentsInstaller enrollmentsInstaller;
    private EnrollmentRepository enrollmentRepository;
    private AppointmentInstaller appointmentInstaller;
    private CalendarCreator calendarCreator;
    private StatusParser statusParser;

    @Value("${enrollment_by_email_and_meeting_id_endpoint}")
    private String ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID_AND_EMAIL;

    @Value("${enrollment_by_parent_id_endpoint}")
    private String ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID;

    @Autowired
    public EnrollmentServiceImpl(TokenService tokenService,
                                 RestTemplate restTemplate,
                                 MeetingService meetingService,
                                 CalendarAttendeesInstaller calendarAttendeesInstaller,
                                 MessageSender messageSender,
                                 InvitationTemplateService invitationTemplateService,
                                 EnrollmentsInstaller enrollmentsInstaller,
                                 EnrollmentRepository enrollmentRepository,
                                 AppointmentInstaller appointmentInstaller, CalendarCreator calendarCreator,
                                 StatusParser statusParser) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.meetingService = meetingService;
        this.calendarAttendeesInstaller = calendarAttendeesInstaller;
        this.messageSender = messageSender;
        this.invitationTemplateService = invitationTemplateService;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.enrollmentRepository = enrollmentRepository;
        this.appointmentInstaller = appointmentInstaller;
        this.calendarCreator = calendarCreator;
        this.statusParser = statusParser;
    }

    @Override
    public Enrollment getEnrollmentByEmailAndParentId(HttpServletRequest request, BigInteger parentId, String
            email) {
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
    public List<Enrollment> getEnrollmentsByParentId(HttpServletRequest request, BigInteger parentId) {
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
    public void enrollLearners(HttpServletRequest request,
                               String meetingId,
                               List<Learner> learners) {
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        String invitationTemplateKey = meeting.getInvitationTemplate();
        if (invitationTemplateKey.isEmpty()) {
            logger.error("Can't enroll learners to this event, cause can't find some invitation template by meeting id: " + meetingId);
            throw new ServiceException("Meeting " + meetingId + " doesn't have learner invitation template");
        }
        enrollmentsInstaller.install(learners, meetingId);
    }

    @Override
    public List<ResponseStatus> sendCalendar(HttpServletRequest request, String meetingId) {
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        String invitationTemplateKey = meeting.getInvitationTemplate();
        if (invitationTemplateKey.isEmpty()) {
            logger.error("Can't enroll learners to this event, cause can't find some invitation template by meeting id: " + meetingId);
            throw new ServiceException("Meeting " + meetingId + " doesn't have learner invitation template");
        }
        InvitationTemplate invitationTemplate = invitationTemplateService.getInvitationTemplateByCode(request, invitationTemplateKey);
        Appointment appointment = appointmentInstaller.installAppointment(meeting, invitationTemplate);
        BigInteger bigIntegerMeetingId = new BigInteger(meetingId);
        List<Enrollment> enrollmentList = enrollmentRepository.getAllByParentId(bigIntegerMeetingId);
        List<ResponseStatus> responseStatusList = new ArrayList<>();
        for (Enrollment enrollment : enrollmentList) {
            Calendar calendar = calendarCreator.createCalendar(enrollment, appointment);
            if (calendar == null) {
                responseStatusList.add(new ResponseStatus(false, enrollment.getUserName(), enrollment.getUserEmail()));
            } else {
                calendarAttendeesInstaller.addAttendeeToCalendar(enrollment, calendar);
                ResponseStatus responseStatus = messageSender.sendCalendarToLearner(calendar, meetingId);
                responseStatusList.add(responseStatus);
                if (responseStatus.isDelivered()) {
                    enrollmentsInstaller.installCalendarFields(enrollment, calendar, appointment);
                }
            }
        }
        return responseStatusList;
    }
}