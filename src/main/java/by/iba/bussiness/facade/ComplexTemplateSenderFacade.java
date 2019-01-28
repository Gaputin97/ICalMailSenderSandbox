package by.iba.bussiness.facade;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.status.EnrollmentCalendarStatus;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.calendar.creator.installer.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.creator.VEventCreator;
import by.iba.bussiness.calendar.creator.simple.SimpleMetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.definer.RruleDefiner;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentsInstaller;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.calendar.status.EnrollmentCalendarStatusDefiner;
import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.meeting.type.MeetingTypeDefiner;
import by.iba.bussiness.sender.MailSendingResponseStatus;
import by.iba.bussiness.sender.MessageSender;
import by.iba.bussiness.template.Template;
import by.iba.bussiness.template.installer.TemplateInstaller;
import by.iba.bussiness.template.installer.TemplateStatusInstaller;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class ComplexTemplateSenderFacade {
    private static final Logger logger = LoggerFactory.getLogger(ComplexTemplateSenderFacade.class);
    private MessageSender messageSender;
    private EnrollmentsInstaller enrollmentsInstaller;
    private EnrollmentService enrollmentService;
    private TemplateStatusInstaller templateStatusInstaller;
    private TemplateInstaller templateInstaller;
    private CalendarAttendeesInstaller calendarAttendeesInstaller;
    private VEventCreator VEventCreator;
    private RruleDefiner rruleDefiner;
    private SimpleMetingCalendarTemplateCreator simpleMetingCalendarTemplateCreator;
    private MeetingTypeDefiner meetingTypeDefiner;
    private EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner;


    @Autowired
    public ComplexTemplateSenderFacade(MessageSender messageSender,
                                       EnrollmentsInstaller enrollmentsInstaller,
                                       EnrollmentService enrollmentService,
                                       TemplateStatusInstaller templateStatusInstaller,
                                       TemplateInstaller templateInstaller,
                                       CalendarAttendeesInstaller calendarAttendeesInstaller,
                                       VEventCreator VEventCreator,
                                       RruleDefiner rruleDefiner,
                                       SimpleMetingCalendarTemplateCreator simpleMetingCalendarTemplateCreator,
                                       MeetingTypeDefiner meetingTypeDefiner,
                                       EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner) {
        this.messageSender = messageSender;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.enrollmentService = enrollmentService;
        this.templateStatusInstaller = templateStatusInstaller;
        this.templateInstaller = templateInstaller;
        this.calendarAttendeesInstaller = calendarAttendeesInstaller;
        this.VEventCreator = VEventCreator;
        this.rruleDefiner = rruleDefiner;
        this.simpleMetingCalendarTemplateCreator = simpleMetingCalendarTemplateCreator;
        this.meetingTypeDefiner = meetingTypeDefiner;
        this.enrollmentCalendarStatusDefiner = enrollmentCalendarStatusDefiner;
    }

    public List<MailSendingResponseStatus> sendTemplate(Appointment appointment, Appointment oldAppointment) {
        BigInteger meetingId = appointment.getMeetingId();
        List<MailSendingResponseStatus> mailSendingResponseStatusList = new ArrayList<>();
        List<Enrollment> enrollmentList = enrollmentService.getAllByParentId(meetingId);
        Template installedTemplate = templateInstaller.installCommonPartsOfTemplate(appointment, oldAppointment);
        MeetingType oldMeetingType = null;
        if (oldAppointment != null) {
            List<Session> oldAppSessions = oldAppointment.getSessionList();
            oldMeetingType = meetingTypeDefiner.defineMeetingType(oldAppSessions);
        }
        boolean isOldMeetingSimple = (oldMeetingType == MeetingType.SIMPLE);
        VEvent event = null;
        if (isOldMeetingSimple) {
            List<Session> oldAppSessions = oldAppointment.getSessionList();
            Rrule rrule = rruleDefiner.defineRrule(oldAppSessions);
            event = VEventCreator.createCommonVEventTemplate(rrule, appointment);
        }
        for (Enrollment enrollment : enrollmentList) {
            if (isOldMeetingSimple) {
                String enrollmentCalendarStatus = enrollmentCalendarStatusDefiner.defineEnrollmentCalendarStatus(enrollment);
                Calendar cancelCalendarWithoutAttendee =
                        simpleMetingCalendarTemplateCreator.createSimpleCancellationCalendar(event);
                Calendar cancelCalendarWithAttendee =
                        calendarAttendeesInstaller.installAttendeeToCalendar(enrollment, cancelCalendarWithoutAttendee);
                messageSender.sendCalendarToLearner(cancelCalendarWithAttendee, enrollmentCalendarStatus, oldAppointment);
            }
            if (EnrollmentCalendarStatus.CANCELLATION.equals(enrollment.getCalendarStatus())
                    && EnrollmentStatus.CANCELLED.equals(enrollment.getStatus())) {
                MailSendingResponseStatus badMailSendingResponseStatus =
                        new MailSendingResponseStatus(false, "User has cancelled status. ", enrollment.getUserEmail());
                mailSendingResponseStatusList.add(badMailSendingResponseStatus);
            } else {
                String templateType = templateStatusInstaller.installTemplateType(enrollment, appointment);
                Template template = new Template(installedTemplate);
                template.setType(templateType);
                if (template.getType() == null) {
                    MailSendingResponseStatus badMailSendingResponseStatus =
                            new MailSendingResponseStatus(false, "User has already updated version. ", enrollment.getUserEmail());
                    mailSendingResponseStatusList.add(badMailSendingResponseStatus);
                    logger.info("Not need to send message to " + enrollment.getUserEmail());
                } else {
                    String userEmail = enrollment.getUserEmail();
                    String meetingTitle = appointment.getTitle();
                    MailSendingResponseStatus mailSendingResponseStatus = messageSender.sendTemplate(template, userEmail, meetingTitle);
                    mailSendingResponseStatusList.add(mailSendingResponseStatus);
                    if (mailSendingResponseStatus.isDelivered()) {
                        enrollmentsInstaller.installEnrollmentCalendarFields(enrollment, appointment);
                    }
                }
            }
        }
        return mailSendingResponseStatusList;
    }
}
