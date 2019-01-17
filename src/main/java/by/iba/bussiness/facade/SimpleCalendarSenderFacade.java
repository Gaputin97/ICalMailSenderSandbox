package by.iba.bussiness.facade;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.EnrollmentCalendarStatus;
import by.iba.bussiness.calendar.EnrollmentStatus;
import by.iba.bussiness.calendar.creator.CalendarCreator;
import by.iba.bussiness.calendar.creator.installer.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.creator.installer.CalendarInstaller;
import by.iba.bussiness.calendar.date.helper.model.reccurence.SimpleDateHelper;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentsInstaller;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.service.v1.EnrollmentServiceImpl;
import by.iba.bussiness.sender.MailSendingResponseStatus;
import by.iba.bussiness.sender.MessageSender;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.CalendarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleCalendarSenderFacade {

    private static final Logger logger = LoggerFactory.getLogger(SimpleCalendarSenderFacade.class);
    private CalendarAttendeesInstaller calendarAttendeesInstaller;
    private MessageSender messageSender;
    private EnrollmentsInstaller enrollmentsInstaller;
    private EnrollmentRepository enrollmentRepository;
    private CalendarCreator calendarCreator;
    private CalendarInstaller calendarInstaller;

    @Autowired
    public SimpleCalendarSenderFacade(CalendarAttendeesInstaller calendarAttendeesInstaller,
                                      MessageSender messageSender,
                                      EnrollmentsInstaller enrollmentsInstaller,
                                      EnrollmentRepository enrollmentRepository,
                                      CalendarCreator calendarCreator,
                                      CalendarInstaller calendarInstaller) {
        this.calendarAttendeesInstaller = calendarAttendeesInstaller;
        this.messageSender = messageSender;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.enrollmentRepository = enrollmentRepository;
        this.calendarCreator = calendarCreator;
        this.calendarInstaller = calendarInstaller;
    }

    public List<MailSendingResponseStatus> sendCalendar(Rrule rrule, Appointment appointment) {
        BigInteger meetingId = appointment.getMeetingId();
        List<MailSendingResponseStatus> mailSendingResponseStatusList = new ArrayList<>();
        Calendar installedCalendar = calendarInstaller.installCalendarCommonParts(rrule, appointment);
        List<Enrollment> enrollmentList = enrollmentRepository.getAllByParentId(meetingId);
        for (Enrollment enrollment : enrollmentList) {
            if (EnrollmentCalendarStatus.CANCELLED.equals(enrollment.getCalendarStatus())
                    && EnrollmentStatus.CANCELLED.equals(enrollment.getStatus())) {
                MailSendingResponseStatus badMailSendingResponseStatus =
                        new MailSendingResponseStatus(false, "User has cancelled status. ", enrollment.getUserEmail());
                mailSendingResponseStatusList.add(badMailSendingResponseStatus);
            } else {
                Calendar calendarWithoutAttendee = calendarCreator.createCalendar(installedCalendar, enrollment, appointment);
                if (calendarWithoutAttendee == null) {
                    MailSendingResponseStatus badMailSendingResponseStatus =
                            new MailSendingResponseStatus(false, "User has already updated version. ", enrollment.getUserEmail());
                    mailSendingResponseStatusList.add(badMailSendingResponseStatus);
                    logger.info("Don't need to send message to " + enrollment.getUserEmail());
                } else {
                    Calendar calendarWithAttendee = calendarAttendeesInstaller.addAttendeeToCalendar(enrollment, calendarWithoutAttendee);
                    MailSendingResponseStatus mailSendingResponseStatus = messageSender.sendCalendarToLearner(calendarWithAttendee);
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
