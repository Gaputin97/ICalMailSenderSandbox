package by.iba.bussiness.facade;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.EnrollmentCalendarStatus;
import by.iba.bussiness.calendar.EnrollmentStatus;
import by.iba.bussiness.calendar.creator.CalendarCreator;
import by.iba.bussiness.calendar.creator.installer.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.creator.installer.EventInstaller;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentsInstaller;
import by.iba.bussiness.enrollment.service.EnrollmentService;
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
    private static final String BODY_OPEN_TAG = "<body>";
    private static final String BODY_CLOSE_TAG = "</body>";
    private CalendarAttendeesInstaller calendarAttendeeInstaller;
    private MessageSender messageSender;
    private EnrollmentsInstaller enrollmentsInstaller;
    private EnrollmentService enrollmentService;
    private CalendarCreator calendarCreator;
    private EventInstaller eventInstaller;

    @Autowired
    public SimpleCalendarSenderFacade(CalendarAttendeesInstaller calendarAttendeeInstaller,
                                      MessageSender messageSender,
                                      EnrollmentsInstaller enrollmentsInstaller,
                                      EnrollmentService enrollmentService,
                                      CalendarCreator calendarCreator,
                                      EventInstaller eventInstaller) {
        this.calendarAttendeeInstaller = calendarAttendeeInstaller;
        this.messageSender = messageSender;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.enrollmentService = enrollmentService;
        this.calendarCreator = calendarCreator;
        this.eventInstaller = eventInstaller;
    }

    public List<MailSendingResponseStatus> sendCalendar(Rrule rrule, Appointment appointment) {
        List<MailSendingResponseStatus> mailSendingResponseStatusList = new ArrayList<>();
        BigInteger meetingId = appointment.getMeetingId();
        List<Enrollment> enrollmentList = enrollmentService.getAllByParentId(meetingId);

        VEvent event = eventInstaller.createEventTemplate(rrule, appointment);
        for (Enrollment enrollment : enrollmentList) {
            if (EnrollmentCalendarStatus.CANCELLED.equals(enrollment.getCalendarStatus())
                    && EnrollmentStatus.CANCELLED.equals(enrollment.getStatus())) {
                MailSendingResponseStatus badMailSendingResponseStatus =
                        new MailSendingResponseStatus(false,"User has cancelled status.", enrollment.getUserEmail());
                mailSendingResponseStatusList.add(badMailSendingResponseStatus);
            } else {
                Calendar calendarWithoutAttendee = calendarCreator.createCalendarTemplateWithEvent(event, enrollment, appointment);
                if (calendarWithoutAttendee == null) {
                    MailSendingResponseStatus badMailSendingResponseStatus =
                            new MailSendingResponseStatus(false ,"User has already updated version.", enrollment.getUserEmail());
                    mailSendingResponseStatusList.add(badMailSendingResponseStatus);
                    logger.info("Not need to send message to " + enrollment.getUserEmail());
                } else {
                    Calendar calendarWithAttendee = calendarAttendeeInstaller.addAttendeeToCalendar(enrollment, calendarWithoutAttendee);
                    String richDescription = BODY_OPEN_TAG + appointment.getDescription() + BODY_CLOSE_TAG;
                    MailSendingResponseStatus mailSendingResponseStatus = messageSender.sendCalendarToLearner(calendarWithAttendee, richDescription);
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
