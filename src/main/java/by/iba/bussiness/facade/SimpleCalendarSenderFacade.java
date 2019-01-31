package by.iba.bussiness.facade;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.enrollment.EnrollmentUpdateChecker;
import by.iba.bussiness.calendar.creator.vevent.CalendarCreator;
import by.iba.bussiness.calendar.creator.installer.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.definer.RruleDefiner;
import by.iba.bussiness.calendar.status.EnrollmentCalendarStatus;
import by.iba.bussiness.calendar.status.EnrollmentCalendarStatusDefiner;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentsInstaller;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.enrollment.status.EnrollmentStatusChecker;
import by.iba.bussiness.sender.MailSendingResponseStatus;
import by.iba.bussiness.sender.MessageSender;
import net.fortuna.ical4j.model.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleCalendarSenderFacade {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCalendarSenderFacade.class);
    private CalendarAttendeesInstaller calendarAttendeeInstaller;
    private MessageSender messageSender;
    private EnrollmentsInstaller enrollmentsInstaller;
    private EnrollmentService enrollmentService;
    private EnrollmentUpdateChecker enrollmentUpdateChecker;
    private CalendarCreator calendarCreator;
    private EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner;
    private EnrollmentStatusChecker enrollmentStatusChecker;
    private RruleDefiner rruleDefiner;

    @Autowired
    public SimpleCalendarSenderFacade(CalendarAttendeesInstaller calendarAttendeeInstaller,
                                      MessageSender messageSender,
                                      EnrollmentsInstaller enrollmentsInstaller,
                                      EnrollmentService enrollmentService,
                                      EnrollmentUpdateChecker enrollmentUpdateChecker,
                                      CalendarCreator calendarCreator,
                                      EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner,
                                      EnrollmentStatusChecker enrollmentStatusChecker,
                                      RruleDefiner rruleDefiner) {
        this.calendarAttendeeInstaller = calendarAttendeeInstaller;
        this.messageSender = messageSender;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.enrollmentService = enrollmentService;
        this.enrollmentUpdateChecker = enrollmentUpdateChecker;
        this.calendarCreator = calendarCreator;
        this.enrollmentCalendarStatusDefiner = enrollmentCalendarStatusDefiner;
        this.enrollmentStatusChecker = enrollmentStatusChecker;
        this.rruleDefiner = rruleDefiner;
    }

    public List<MailSendingResponseStatus> sendCalendar(Appointment newAppointment, Appointment currentAppointment) {
        List<MailSendingResponseStatus> mailSendingResponseStatusList = new ArrayList<>();
        BigInteger meetingId = newAppointment.getMeetingId();
        List<Enrollment> enrollmentList = enrollmentService.getAllByParentId(meetingId);
        Calendar invitationCalendar;
        Calendar cancellationCalendar;
        if (enrollmentStatusChecker.doAllEnrollmentHaveCancelledStatus(enrollmentList)) {
            cancellationCalendar = calendarCreator.createCalendarCancellationTemplate(currentAppointment);
            invitationCalendar = null;
        } else {
            Rrule rrule = rruleDefiner.defineRrule(newAppointment.getSessionList());
            invitationCalendar = calendarCreator.createCalendarTemplate(rrule, newAppointment);
            cancellationCalendar = null;
        }
        for (Enrollment enrollment : enrollmentList) {
            if (EnrollmentCalendarStatus.CANCELLATION.equals(enrollment.getCalendarStatus())
                    && EnrollmentStatus.CANCELLED.equals(enrollment.getStatus())) {
                MailSendingResponseStatus badMailSendingResponseStatus =
                        new MailSendingResponseStatus(false, "User has cancelled status.", enrollment.getUserEmail());
                mailSendingResponseStatusList.add(badMailSendingResponseStatus);
            } else {
                boolean isEnrollmentMustBeUpdated = enrollmentUpdateChecker.isEnrollmentMustBeUpdated(enrollment, newAppointment);
                if (isEnrollmentMustBeUpdated == false) {
                    MailSendingResponseStatus badMailSendingResponseStatus =
                            new MailSendingResponseStatus(false, "User has already updated version.", enrollment.getUserEmail());
                    mailSendingResponseStatusList.add(badMailSendingResponseStatus);
                    logger.info("Don't need to send message to " + enrollment.getUserEmail());
                } else {
                    Calendar calendarWithoutAttendee;
                    if (EnrollmentStatus.CANCELLED.equals(enrollment.getStatus())) {
                        calendarWithoutAttendee = cancellationCalendar;
                    } else {
                        calendarWithoutAttendee = invitationCalendar;
                    }
                    Calendar calendarWithAttendee = calendarAttendeeInstaller.installAttendeeToCalendar(enrollment.getUserEmail(), calendarWithoutAttendee);
                    String enrollmentCalendarStatus = enrollmentCalendarStatusDefiner.defineEnrollmentCalendarStatus(enrollment);
                    MailSendingResponseStatus mailSendingResponseStatus =
                            messageSender.sendCalendarToLearner(calendarWithAttendee, enrollmentCalendarStatus, newAppointment);
                    mailSendingResponseStatusList.add(mailSendingResponseStatus);
                    if (mailSendingResponseStatus.isDelivered()) {
                        enrollmentsInstaller.installEnrollmentCalendarFields(enrollment, newAppointment);
                    }
                }
            }
        }
        return mailSendingResponseStatusList;
    }
}
