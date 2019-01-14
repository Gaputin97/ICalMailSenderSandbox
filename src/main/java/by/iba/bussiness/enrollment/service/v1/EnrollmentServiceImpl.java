package by.iba.bussiness.enrollment.service.v1;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.AppointmentInstaller;
import by.iba.bussiness.calendar.date.DateHelperDefiner;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enrollment.EnrollmentLearnerStatus;
import by.iba.bussiness.enrollment.EnrollmentsInstaller;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.facade.ComplexCalendarSenderFacade;
import by.iba.bussiness.facade.SimpleCalendarSenderFacade;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.invitation_template.service.InvitationTemplateService;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.MeetingType;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.sender.MailSendingResponseStatus;
import by.iba.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("endpoint.properties")
public class EnrollmentServiceImpl implements EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    private MeetingService meetingService;
    private InvitationTemplateService invitationTemplateService;
    private EnrollmentsInstaller enrollmentsInstaller;
    private AppointmentInstaller appointmentInstaller;
    private DateHelperDefiner dateHelperDefiner;
    private ComplexCalendarSenderFacade complexCalendarSenderFacade;
    private SimpleCalendarSenderFacade simpleCalendarSenderFacade;

    @Value("${enrollment_by_email_and_meeting_id_endpoint}")
    private String ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID_AND_EMAIL;

    @Value("${enrollment_by_parent_id_endpoint}")
    private String ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID;

    @Autowired
    public EnrollmentServiceImpl(MeetingService meetingService,
                                 InvitationTemplateService invitationTemplateService,
                                 EnrollmentsInstaller enrollmentsInstaller,
                                 AppointmentInstaller appointmentInstaller,
                                 DateHelperDefiner dateHelperDefiner,
                                 ComplexCalendarSenderFacade complexCalendarSenderFacade,
                                 SimpleCalendarSenderFacade simpleCalendarSenderFacade) {
        this.meetingService = meetingService;
        this.invitationTemplateService = invitationTemplateService;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.appointmentInstaller = appointmentInstaller;
        this.dateHelperDefiner = dateHelperDefiner;
        this.complexCalendarSenderFacade = complexCalendarSenderFacade;
        this.simpleCalendarSenderFacade = simpleCalendarSenderFacade;
    }

    @Override
    public List<EnrollmentLearnerStatus> enrollLearners(HttpServletRequest request,
                                                        String meetingId,
                                                        List<Learner> learners) {
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        String invitationTemplateKey = meeting.getInvitationTemplate();
        if (invitationTemplateKey.isEmpty()) {
            logger.error("Can't enroll learners to this event, cause can't find some invitation template by meeting id: " + meetingId);
            throw new ServiceException("Meeting " + meetingId + " doesn't have learner invitation template");
        }
        return enrollmentsInstaller.installEnrollmentsByLearners(learners, meetingId);
    }

    @Override
    public List<MailSendingResponseStatus> sendCalendarToAllEnrollmentsOfMeeting(HttpServletRequest request, String meetingId) {
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        if (meeting == null) {
            logger.info("Can't find meeting in ec3 with meetingId: " + meetingId);
            throw new ServiceException("Can't find meeting with id " + meetingId);
        }
        String invitationTemplateKey = meeting.getInvitationTemplate();
        if (invitationTemplateKey.isEmpty()) {
            logger.error("Can't enroll learners to this event, cause can't find some invitation template by meeting id: " + meetingId);
            throw new ServiceException("Meeting " + meetingId + " doesn't have learner invitation template");
        }
        InvitationTemplate invitationTemplate = invitationTemplateService.getInvitationTemplateByCode(request, invitationTemplateKey);
        Appointment appointment = appointmentInstaller.installAppointment(meeting, invitationTemplate);
        List<MailSendingResponseStatus> mailSendingResponseStatusList = new ArrayList<>();
        DateHelper dateHelper = dateHelperDefiner.defineDateHelper(appointment.getTimeSlots());
        if (dateHelper.getMeetingType().equals(MeetingType.RECURRENCE)) {
            mailSendingResponseStatusList = simpleCalendarSenderFacade.sendCalendar(dateHelper, appointment);
        } else {
            mailSendingResponseStatusList = complexCalendarSenderFacade.sendCalendar(dateHelper, appointment);
        }
    }
}