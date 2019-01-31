package by.iba.bussiness.facade;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.creator.CalendarCreator;
import by.iba.bussiness.calendar.creator.vevent.VEventCreator;
import by.iba.bussiness.calendar.creator.installer.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.status.EnrollmentCalendarStatus;
import by.iba.bussiness.calendar.status.EnrollmentCalendarStatusDefiner;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentsInstaller;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import by.iba.bussiness.sender.MailSendingResponseStatus;
import by.iba.bussiness.sender.MessageSender;
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
public class SimpleCalendarSenderFacade {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCalendarSenderFacade.class);
    private CalendarAttendeesInstaller calendarAttendeeInstaller;
    private MessageSender messageSender;
    private EnrollmentsInstaller enrollmentsInstaller;
    private EnrollmentService enrollmentService;
    private CalendarCreator calendarCreator;
    private VEventCreator VEventCreator;
    private EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner;

    @Autowired
    public SimpleCalendarSenderFacade(CalendarAttendeesInstaller calendarAttendeeInstaller,
                                      MessageSender messageSender,
                                      EnrollmentsInstaller enrollmentsInstaller,
                                      EnrollmentService enrollmentService,
                                      CalendarCreator calendarCreator,
                                      VEventCreator VEventCreator,
                                      EnrollmentCalendarStatusDefiner enrollmentCalendarStatusDefiner) {
        this.calendarAttendeeInstaller = calendarAttendeeInstaller;
        this.messageSender = messageSender;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.enrollmentService = enrollmentService;
        this.calendarCreator = calendarCreator;
        this.VEventCreator = VEventCreator;
        this.enrollmentCalendarStatusDefiner = enrollmentCalendarStatusDefiner;
    }

    public List<MailSendingResponseStatus> sendCalendar(Rrule rrule, Appointment newAppointment, Appointment oldAppointment) {
        List<MailSendingResponseStatus> mailSendingResponseStatusList = new ArrayList<>();
        BigInteger meetingId = newAppointment.getMeetingId();
        List<Enrollment> enrollmentList = enrollmentService.getAllByParentId(meetingId);
        VEvent event = VEventCreator.createCommonVEventTemplate(rrule, newAppointment);
        VEvent cancellationEvent = VEventCreator.createCommonVEventCancellationTemplate(oldAppointment);
        for (Enrollment enrollment : enrollmentList) {
            if (EnrollmentCalendarStatus.CANCELLATION.equals(enrollment.getCalendarStatus())
                    && EnrollmentStatus.CANCELLED.equals(enrollment.getStatus())) {
                MailSendingResponseStatus badMailSendingResponseStatus =
                        new MailSendingResponseStatus(false, "User has cancelled status.", enrollment.getUserEmail());
                mailSendingResponseStatusList.add(badMailSendingResponseStatus);
            } else {
                Calendar calendarWithoutAttendee = calendarCreator.createConcreteCalendarTemplate(event, enrollment, newAppointment);
                if (calendarWithoutAttendee == null) {
                    MailSendingResponseStatus badMailSendingResponseStatus =
                            new MailSendingResponseStatus(false, "User has already updated version.", enrollment.getUserEmail());
                    mailSendingResponseStatusList.add(badMailSendingResponseStatus);
                    logger.info("Don't need to send message to " + enrollment.getUserEmail());
                } else {
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
