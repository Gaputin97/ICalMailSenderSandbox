package by.iba.bussiness.facade;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.handler.IndexDeterminer;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.enrollment.EnrollmentUpdateChecker;
import by.iba.bussiness.calendar.creator.vevent.CalendarCreator;
import by.iba.bussiness.calendar.creator.installer.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.definer.RruleDefiner;
import by.iba.bussiness.calendar.status.CalendarStatus;
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
    private EnrollmentService enrollmentService;
    private EnrollmentUpdateChecker enrollmentUpdateChecker;
    private CalendarCreator calendarCreator;
    private EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner;
    private RruleDefiner rruleDefiner;
    private IndexDeterminer indexDeterminer;
    private EnrollmentsInstaller enrollmentsInstaller;
    private EnrollmentStatusChecker enrollmentStatusChecker;

    @Autowired
    public SimpleCalendarSenderFacade(CalendarAttendeesInstaller calendarAttendeeInstaller,
                                      MessageSender messageSender,
                                      EnrollmentService enrollmentService,
                                      EnrollmentUpdateChecker enrollmentUpdateChecker,
                                      CalendarCreator calendarCreator,
                                      EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner,
                                      RruleDefiner rruleDefiner,
                                      IndexDeterminer indexDeterminer,
                                      EnrollmentsInstaller enrollmentsInstaller,
                                      EnrollmentStatusChecker enrollmentStatusChecker) {
        this.calendarAttendeeInstaller = calendarAttendeeInstaller;
        this.messageSender = messageSender;
        this.enrollmentService = enrollmentService;
        this.enrollmentUpdateChecker = enrollmentUpdateChecker;
        this.calendarCreator = calendarCreator;
        this.enrollmentCalendarStatusDefiner = enrollmentCalendarStatusDefiner;
        this.rruleDefiner = rruleDefiner;
        this.indexDeterminer = indexDeterminer;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.enrollmentStatusChecker = enrollmentStatusChecker;
    }

    public List<NotificationResponseStatus> sendCalendar(Appointment newAppointment, Appointment currentAppointment) {
        BigInteger meetingId = newAppointment.getMeetingId();
        List<Session> sessionList = newAppointment.getSessionList();

        List<Enrollment> enrollmentList = enrollmentService.getAllByParentId(meetingId);
        Calendar cancellationCalendar = calendarCreator.createCancellationTemplate(currentAppointment);
        Calendar invitationCalendar = null;

        if (enrollmentStatusChecker.isAnyEnrollmentHasConfirmedStatus(enrollmentList)) {
            Rrule rrule = rruleDefiner.defineRrule(sessionList);
            invitationCalendar = calendarCreator.createCalendarTemplate(rrule, newAppointment);
        }

        List<NotificationResponseStatus> notificationResponseStatusList = new ArrayList<>();
        for (Enrollment enrollment : enrollmentList) {
            String enrollmentStatus = enrollment.getStatus();
            String enrollmentEmail = enrollment.getUserEmail();
            if (CalendarStatus.CANCELLATION.name().equals(enrollment.getCalendarStatus())
                    && EnrollmentStatus.CANCELLED.name().equals(enrollmentStatus)) {
                NotificationResponseStatus badResponseStatus =
                        new NotificationResponseStatus(false,"User already has sanded cancelled calendar notification.", enrollmentEmail);
                notificationResponseStatusList.add(badResponseStatus);
            } else {
                String enrollmentCalendarVersion = enrollment.getCalendarVersion();
                int maxIndex = indexDeterminer.getMaxIndex(newAppointment);
                boolean isEnrollmentMustBeUpdated = enrollmentUpdateChecker.isMustBeUpdated(enrollmentCalendarVersion, maxIndex);

                if (isEnrollmentMustBeUpdated == false) {
                    NotificationResponseStatus badResponseStatus = new NotificationResponseStatus(false, "User has already updated version.", enrollmentEmail);
                    notificationResponseStatusList.add(badResponseStatus);
                    logger.info("Don't need to send message to " + enrollmentEmail + " cause this user already has needed version of calendar");
                } else {
                    Calendar calendarWithoutAttendee;
                    if (EnrollmentStatus.CANCELLED.name().equals(enrollmentStatus)) {
                        calendarWithoutAttendee = cancellationCalendar;
                    } else {
                        calendarWithoutAttendee = invitationCalendar;
                    }

                    Calendar calendarWithAttendee = calendarAttendeeInstaller.installAttendeeToTheCalendar(enrollmentEmail, calendarWithoutAttendee);
                    String enrollmentCalendarStatus = enrollmentCalendarStatusDefiner.defineEnrollmentCalendarStatus(enrollment);
                    NotificationResponseStatus notificationResponseStatus = messageSender.sendCalendar(calendarWithAttendee, enrollmentCalendarStatus, newAppointment);
                    notificationResponseStatusList.add(notificationResponseStatus);

                    if (notificationResponseStatus.isDelivered()) {
                        Enrollment updatedEnrollment = enrollmentsInstaller.installEnrollmentCalendarFields(enrollment, maxIndex, enrollmentCalendarStatus);
                        enrollmentService.save(updatedEnrollment);
                    }
                }
            }
        }
        return notificationResponseStatusList;
    }
}
