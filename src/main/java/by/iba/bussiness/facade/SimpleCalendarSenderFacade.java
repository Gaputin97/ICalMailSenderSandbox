package by.iba.bussiness.facade;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.EnrollmentCalendarStatus;
import by.iba.bussiness.calendar.EnrollmentStatus;
import by.iba.bussiness.calendar.creator.CalendarCreator;
import by.iba.bussiness.calendar.creator.installer.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.creator.installer.CalendarInstaller;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.EnrollmentsInstaller;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.enrollment.service.v1.EnrollmentServiceImpl;
import by.iba.bussiness.sender.MailSendingResponseStatus;
import by.iba.bussiness.sender.SenderMessage;
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
    private CalendarAttendeesInstaller calendarAttendeesInstaller;
    private SenderMessage senderMessage;
    private EnrollmentsInstaller enrollmentsInstaller;
    private EnrollmentService enrollmentService;
    private CalendarCreator calendarCreator;
    private CalendarInstaller calendarInstaller;

    @Autowired
    public SimpleCalendarSenderFacade(CalendarAttendeesInstaller calendarAttendeesInstaller,
                                      SenderMessage senderMessage,
                                      EnrollmentsInstaller enrollmentsInstaller,
                                      EnrollmentService enrollmentService,
                                      CalendarCreator calendarCreator,
                                      CalendarInstaller calendarInstaller) {
        this.calendarAttendeesInstaller = calendarAttendeesInstaller;
        this.senderMessage = senderMessage;
        this.enrollmentsInstaller = enrollmentsInstaller;
        this.enrollmentService = enrollmentService;
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
                    logger.info("Not need to send message to " + enrollment.getUserEmail());
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
