package by.iba.bussiness.sender;

import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.creator.CalendarEnrollmentCreator;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.meeting.Meeting;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@org.springframework.stereotype.Component
public class MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
    private JavaMailSender javaMailSender;
    private CalendarTextEditor calendarTextEditor;
    private EnrollmentRepository enrollmentRepository;
    private StatusParser statusParser;
    private CalendarEnrollmentCreator calendarEnrollmentCreator;

    @Autowired
    public MessageSender(JavaMailSender javaMailSender, CalendarTextEditor calendarTextEditor, EnrollmentRepository enrollmentRepository, StatusParser statusParser, CalendarEnrollmentCreator calendarEnrollmentCreator) {
        this.javaMailSender = javaMailSender;
        this.calendarTextEditor = calendarTextEditor;
        this.enrollmentRepository = enrollmentRepository;
        this.statusParser = statusParser;
        this.calendarEnrollmentCreator = calendarEnrollmentCreator;
    }

    public ResponseStatus sendCalendarToLearner(Calendar calendar, String meetingId) {
        MimeMessage message;
        VEvent event = (VEvent) calendar.getComponents().getComponent(Component.VEVENT);
        Attendee attendee = event.getProperties().getProperty(Property.ATTENDEE);
        String userName = attendee.getName();
        String userEmail = attendee.getCalAddress().toString();
        String editedUserEmail = calendarTextEditor.editUserEmail(userEmail);
        ResponseStatus responseStatus;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Method method = calendar.getMethod();
            String stringMethod = calendarTextEditor.replaceColonToEqual(method.toString());

            helper.setTo(editedUserEmail);

            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart iCalInline = new MimeBodyPart();
            iCalInline.setHeader("Content-class", "urn:content-classes:calendarmessage");
            iCalInline.setHeader("Content-ID", "<calendar_part>");
            iCalInline.setHeader("Content-Disposition", "inline");
            iCalInline.setContent(calendar.toString(), "text/calendar;charset=utf-8;" + stringMethod);

            iCalInline.setFileName("inlineCalendar.ics");
            multipart.addBodyPart(iCalInline);

            MimeBodyPart iCalAttachment = new MimeBodyPart();
            iCalAttachment.setHeader("Content-class", "urn:content-classes:calendarmessage");
            iCalAttachment.setHeader("Content-Disposition", "attachment");
            iCalAttachment.setContent(calendar.toString(), "text/calendar;charset=utf-8;" + stringMethod);
            iCalAttachment.setFileName("attachedCalendar.ics");
            multipart.addBodyPart(iCalAttachment);
            message.setContent(multipart);

            javaMailSender.send(message);

            logger.info("Message was sanded to " + editedUserEmail);
            Enrollment enrollment = enrollmentRepository.getByEmailAndParentIdAndType(meetingId, editedUserEmail, statusParser.parseCalMethodToEnrollmentStatus(method));
            enrollment.setCalendarStatus(statusParser.parseCalMethodToEnrollmentCalendarStatus(method));
            enrollment.setCalendarVersion(event.getSequence().getValue());
            enrollmentRepository.save(enrollment);
            logger.info("New enrollment with meeting id" + meetingId + " and user " + editedUserEmail + " was added");
            responseStatus = new ResponseStatus(true, userName, editedUserEmail);
        } catch (MessagingException e) {
            logger.error("Error while trying to send message", e);
            responseStatus = new ResponseStatus(false, userName, editedUserEmail);
        }
        return responseStatus;
    }
}