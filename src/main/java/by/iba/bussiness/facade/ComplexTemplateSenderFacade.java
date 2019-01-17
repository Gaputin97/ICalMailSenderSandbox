package by.iba.bussiness.facade;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.EnrollmentCalendarStatus;
import by.iba.bussiness.calendar.EnrollmentStatus;
import by.iba.bussiness.calendar.creator.installer.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.creator.installer.CalendarInstaller;
import by.iba.bussiness.calendar.creator.recurrence.RecurrenceMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.definer.RruleDefiner;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentsInstaller;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.service.v1.EnrollmentServiceImpl;
import by.iba.bussiness.meeting.MeetingType;
import by.iba.bussiness.sender.MailSendingResponseStatus;
import by.iba.bussiness.sender.MessageSender;
import by.iba.bussiness.template.Template;
import by.iba.bussiness.template.installer.TemplateInstaller;
import by.iba.bussiness.template.installer.TemplateStatusInstaller;
import net.fortuna.ical4j.model.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class ComplexTemplateSenderFacade {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    private MessageSender messageSender;
    private EnrollmentsInstaller enrollmentsInstaller;
    private EnrollmentRepository enrollmentRepository;
    private TemplateStatusInstaller templateStatusInstaller;
    private TemplateInstaller templateInstaller;
    private CalendarAttendeesInstaller calendarAttendeesInstaller;
    private CalendarInstaller calendarInstaller;
    private RruleDefiner rruleDefiner;
    private RecurrenceMeetingCalendarTemplateCreator recurrenceMeetingCalendarTemplateCreator;


    @Autowired
    public ComplexTemplateSenderFacade(MessageSender messageSender,
                                       EnrollmentsInstaller enrollmentsInstaller,
                                       EnrollmentRepository enrollmentRepository,
                                       TemplateStatusInstaller templateStatusInstaller,
                                       TemplateInstaller templateInstaller,
                                       CalendarAttendeesInstaller calendarAttendeesInstaller,
                                       CalendarInstaller calendarInstaller,
                                       RruleDefiner rruleDefiner,
                                       RecurrenceMeetingCalendarTemplateCreator recurrenceMeetingCalendarTemplateCreator) {
        this.messageSender = messageSender;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.enrollmentRepository = enrollmentRepository;
        this.templateStatusInstaller = templateStatusInstaller;
        this.templateInstaller = templateInstaller;
        this.calendarAttendeesInstaller = calendarAttendeesInstaller;
        this.calendarInstaller = calendarInstaller;
        this.rruleDefiner = rruleDefiner;
        this.recurrenceMeetingCalendarTemplateCreator = recurrenceMeetingCalendarTemplateCreator;
    }

    public List<MailSendingResponseStatus> sendTemplate(Appointment appointment, Appointment oldAppointment, MeetingType oldMeetingType) {
        BigInteger meetingId = appointment.getMeetingId();
        List<MailSendingResponseStatus> mailSendingResponseStatusList = new ArrayList<>();
        List<Enrollment> enrollmentList = enrollmentRepository.getAllByParentId(meetingId);
        Template installedTemplate = new Template();
        templateInstaller.installCommonPartsOfTemplate(appointment, oldAppointment, installedTemplate);
        if (oldMeetingType == MeetingType.SIMPLE) {
            List<Session> sessions = null;
            Calendar cancelCalendar = new Calendar();
            Rrule rrule = rruleDefiner.defineRrule(sessions);
            calendarInstaller.installCalendarCommonParts(rrule, appointment, cancelCalendar);
        }
        for (Enrollment enrollment : enrollmentList) {
            if (oldMeetingType == MeetingType.SIMPLE) {
                Calendar cancelCalendar = new Calendar();
                recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarCancellationTemplate(cancelCalendar);
                calendarAttendeesInstaller.addAttendeeToCalendar(enrollment, cancelCalendar);
                messageSender.sendCalendarToLearner(cancelCalendar);
            }
            if (EnrollmentCalendarStatus.CANCELLED.equals(enrollment.getCalendarStatus())
                    && EnrollmentStatus.CANCELLED.equals(enrollment.getStatus())) {
                MailSendingResponseStatus badMailSendingResponseStatus =
                        new MailSendingResponseStatus(false, "User has cancelled status. ", enrollment.getUserEmail());
                mailSendingResponseStatusList.add(badMailSendingResponseStatus);
            } else {
                Template template = installedTemplate;
                templateStatusInstaller.installTemplateType(enrollment, appointment, template);
                if (template.getType() == null) {
                    MailSendingResponseStatus badMailSendingResponseStatus =
                            new MailSendingResponseStatus(false, "User has already updated version. ", enrollment.getUserEmail());
                    mailSendingResponseStatusList.add(badMailSendingResponseStatus);
                    logger.info("Don't need to send message to " + enrollment.getUserEmail());
                } else {
                    String userEmail = enrollment.getUserEmail();
                    MailSendingResponseStatus mailSendingResponseStatus = messageSender.sendTemplate(template, userEmail);
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
