package by.iba.bussiness.facade;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.determiner.IndexDeterminer;
import by.iba.bussiness.calendar.creator.CalendarCreator;
import by.iba.bussiness.calendar.creator.installer.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.definer.RruleDefiner;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.status.CalendarStatus;
import by.iba.bussiness.calendar.status.EnrollmentCalendarStatusDefiner;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentsInstaller;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.meeting.type.MeetingTypeDefiner;
import by.iba.bussiness.notification.NotificationResponseStatus;
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
    private static final Logger logger = LoggerFactory.getLogger(ComplexTemplateSenderFacade.class);
    private MessageSender messageSender;
    private EnrollmentsInstaller enrollmentsInstaller;
    private EnrollmentService enrollmentService;
    private TemplateStatusInstaller templateStatusInstaller;
    private TemplateInstaller templateInstaller;
    private CalendarAttendeesInstaller calendarAttendeesInstaller;
    private CalendarCreator CalendarCreator;
    private RruleDefiner rruleDefiner;
    private MeetingTypeDefiner meetingTypeDefiner;
    private EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner;
    private IndexDeterminer indexDeterminer;


    @Autowired
    public ComplexTemplateSenderFacade(MessageSender messageSender,
                                       EnrollmentsInstaller enrollmentsInstaller,
                                       EnrollmentService enrollmentService,
                                       TemplateStatusInstaller templateStatusInstaller,
                                       TemplateInstaller templateInstaller,
                                       CalendarAttendeesInstaller calendarAttendeesInstaller,
                                       CalendarCreator CalendarCreator,
                                       RruleDefiner rruleDefiner,
                                       MeetingTypeDefiner meetingTypeDefiner,
                                       EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner,
                                       IndexDeterminer indexDeterminer) {
        this.messageSender = messageSender;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.enrollmentService = enrollmentService;
        this.templateStatusInstaller = templateStatusInstaller;
        this.templateInstaller = templateInstaller;
        this.calendarAttendeesInstaller = calendarAttendeesInstaller;
        this.CalendarCreator = CalendarCreator;
        this.rruleDefiner = rruleDefiner;
        this.meetingTypeDefiner = meetingTypeDefiner;
        this.enrollmentCalendarStatusDefiner = enrollmentCalendarStatusDefiner;
        this.indexDeterminer = indexDeterminer;
    }

    public List<NotificationResponseStatus> sendTemplate(Appointment newAppointment, Appointment currentAppointment) {
        BigInteger meetingId = newAppointment.getMeetingId();
        List<NotificationResponseStatus> notificationResponseStatusList = new ArrayList<>();
        List<Enrollment> enrollmentList = enrollmentService.getAllByParentId(meetingId);
        Template installedTemplate = templateInstaller.installTemplate(newAppointment, currentAppointment);
        MeetingType oldMeetingType = null;
        if (currentAppointment != null) {
            List<Session> oldAppSessions = currentAppointment.getSessionList();
            oldMeetingType = meetingTypeDefiner.defineMeetingType(oldAppSessions);
        }
        boolean isOldMeetingSimple = (oldMeetingType == MeetingType.SIMPLE);
        Calendar calendar = null;
        if (isOldMeetingSimple) {
            List<Session> oldAppSessions = currentAppointment.getSessionList();
            Rrule rrule = rruleDefiner.defineRrule(oldAppSessions);
            calendar = CalendarCreator.createCalendarTemplate(rrule, newAppointment);
        }

        for (Enrollment enrollment : enrollmentList) {
            String enrollmentEmail = enrollment.getUserEmail();
            if (isOldMeetingSimple) {
                String enrollmentCalendarStatus = enrollmentCalendarStatusDefiner.defineEnrollmentCalendarStatus(enrollment);
                Calendar cancelCalendarWithAttendee =
                        calendarAttendeesInstaller.installAttendeeToTheCalendar(enrollmentEmail, calendar);
                messageSender.sendCalendar(cancelCalendarWithAttendee, enrollmentCalendarStatus, currentAppointment);
            }

            if (CalendarStatus.CANCELLATION.equals(enrollment.getCalendarStatus())
                    && EnrollmentStatus.CANCELLED.equals(enrollment.getStatus())) {
                NotificationResponseStatus badNotificationResponseStatus =
                        new NotificationResponseStatus(false, "User has cancelled status. ", enrollmentEmail);
                notificationResponseStatusList.add(badNotificationResponseStatus);
            } else {
                String templateType = templateStatusInstaller.installTemplateType(enrollment, newAppointment);
                Template template = new Template(installedTemplate);
                template.setType(templateType);

                if (template.getType() == null) {
                    NotificationResponseStatus badNotificationResponseStatus =
                            new NotificationResponseStatus(false, "User has already updated version. ", enrollmentEmail);
                    notificationResponseStatusList.add(badNotificationResponseStatus);
                    logger.info("Not need to send message to " + enrollmentEmail);
                } else {
                    String userEmail = enrollmentEmail;
                    String meetingTitle = newAppointment.getTitle();
                    NotificationResponseStatus notificationResponseStatus = messageSender.sendTemplate(template, userEmail, meetingTitle);
                    notificationResponseStatusList.add(notificationResponseStatus);

                    if (notificationResponseStatus.isDelivered()) {
                        int maxIndex = indexDeterminer.getMaxIndex(newAppointment);
                        String enrollmentCalendarStatus = enrollmentCalendarStatusDefiner.defineEnrollmentCalendarStatus(enrollment);
                        Enrollment updatedEnrollment = enrollmentsInstaller.installEnrollmentCalendarFields(enrollment, maxIndex, enrollmentCalendarStatus);
                        enrollmentService.save(updatedEnrollment);
                    }
                }
            }
        }
        return notificationResponseStatusList;
    }
}
