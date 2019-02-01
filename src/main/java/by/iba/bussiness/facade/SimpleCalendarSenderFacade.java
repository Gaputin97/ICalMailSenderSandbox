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
import by.iba.bussiness.notification.NotificationResponseStatus;
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

    public List<NotificationResponseStatus> sendCalendar(Appointment newAppointment, Appointment currentAppointment) {
        BigInteger meetingId = newAppointment.getMeetingId();
        List<Enrollment> enrollmentList = enrollmentService.getAllByParentId(meetingId);

        Calendar invitationCalendar = null;
        Calendar cancellationCalendar = null;
        if (enrollmentStatusChecker.areAllEnrollmentsHasCancelledStatus(enrollmentList)) {
            cancellationCalendar = calendarCreator.createCalendarCancellationTemplate(currentAppointment);
        } else {
            Rrule rrule = rruleDefiner.defineRrule(newAppointment.getSessionList());
            invitationCalendar = calendarCreator.createCalendarTemplate(rrule, newAppointment);
        }
        List<NotificationResponseStatus> notificationResponseStatusList = new ArrayList<>();
        for (Enrollment enrollment : enrollmentList) {
            if (EnrollmentCalendarStatus.CANCELLATION.name().equals(enrollment.getCalendarStatus())
                    && EnrollmentStatus.CANCELLED.name().equals(enrollment.getStatus())) {
                NotificationResponseStatus badNotificationResponseStatus =
                        new NotificationResponseStatus(false, "User has cancelled status.", enrollment.getUserEmail());
                notificationResponseStatusList.add(badNotificationResponseStatus);
            } else {
                boolean isEnrollmentMustBeUpdated = enrollmentUpdateChecker.isEnrollmentMustBeUpdated(enrollment, newAppointment);
                if (isEnrollmentMustBeUpdated == false) {
                    NotificationResponseStatus badNotificationResponseStatus =
                            new NotificationResponseStatus(false, "User has already updated version.", enrollment.getUserEmail());
                    notificationResponseStatusList.add(badNotificationResponseStatus);
                    logger.info("Don't need to send message to " + enrollment.getUserEmail());
                } else {
                    Calendar calendarWithoutAttendee;
                    if (EnrollmentStatus.CANCELLED.name().equals(enrollment.getStatus())) {
                        calendarWithoutAttendee = cancellationCalendar;
                    } else {
                        calendarWithoutAttendee = invitationCalendar;
                    }

                    Calendar calendarWithAttendee = calendarAttendeeInstaller.installAttendeeToCalendar(enrollment.getUserEmail(), calendarWithoutAttendee);
                    String enrollmentCalendarStatus = enrollmentCalendarStatusDefiner.defineEnrollmentCalendarStatus(enrollment);
                    NotificationResponseStatus notificationResponseStatus =
                            messageSender.sendCalendarToLearner(calendarWithAttendee, enrollmentCalendarStatus, newAppointment);
                    notificationResponseStatusList.add(notificationResponseStatus);
                    if (notificationResponseStatus.isDelivered()) {
                        enrollmentsInstaller.installEnrollmentCalendarFields(enrollment, newAppointment);
                    }
                }
            }
        }
        return notificationResponseStatusList;
    }
}
