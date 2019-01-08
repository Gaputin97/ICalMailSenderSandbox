package by.iba.bussiness.sender;

import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.creator.CalendarEnrollmentCreator;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.response.CalendarSendingResponse;
import by.iba.bussiness.sender.parser.StatusParser;
import by.iba.exception.SendingException;
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
import java.math.BigInteger;
import java.util.List;

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

    public void sendMessageToOneRecipientAndSaveEnrollment(Calendar calendar, Meeting meeting) {
        MimeMessage message;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            VEvent event = (VEvent) calendar.getComponents().getComponent(Component.VEVENT);
            Property attendee = event.getProperties().getProperty(Property.ATTENDEE);
            Method method = calendar.getMethod();
            String address = ((Attendee) attendee).getCalAddress().toString();
            String stringMethod = calendarTextEditor.replaceColonToEqual(method.toString());
            String editedUserEmail = calendarTextEditor.editUserEmail(address);
            helper.setTo(editedUserEmail);

            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart iСalInline = new MimeBodyPart();
            iСalInline.setHeader("Content-class", "urn:content-classes:calendarmessage");
            iСalInline.setHeader("Content-ID", "<calendar_part>");
            iСalInline.setHeader("Content-Disposition", "inline");
            iСalInline.setContent(calendar.toString(), "text/calendar;charset=utf-8;" + stringMethod);

            iСalInline.setFileName("inlineCalendar.ics");
            multipart.addBodyPart(iСalInline);

            MimeBodyPart iСalAttachment = new MimeBodyPart();
            iСalAttachment.setHeader("Content-class", "urn:content-classes:calendarmessage");
            iСalAttachment.setHeader("Content-Disposition", "attachment");
            iСalAttachment.setContent(calendar.toString(), "text/calendar;charset=utf-8;" + stringMethod);
            iСalAttachment.setFileName("attachedCalendar.ics");
            multipart.addBodyPart(iСalAttachment);
            message.setContent(multipart);

            javaMailSender.send(message);

            logger.info("Message was sended to " + editedUserEmail);
            BigInteger meetingId = meeting.getId();
            Enrollment enrollment = enrollmentRepository.getByEmailAndParentIdAndType(meetingId, editedUserEmail, statusParser.parseCalMethodToEnrollmentStatus(method));
            enrollment.setCalendarStatus(statusParser.parseCalMethodToEnrollmentCalendarStatus(method));
            enrollment.setCalendarVersion(event.getSequence().getValue());
            enrollmentRepository.save(enrollment);
            logger.info("New enrollment with meeting id" + meeting.getId() + " and user " + editedUserEmail + " was added");
        } catch (MessagingException e) {
            logger.error("Error while trying to send message", e);
            throw new SendingException("Error while trying to send message.");
        }
    }

    public CalendarSendingResponse sendMessageToAllRecipientsAndSaveEnrollments(List<Calendar> calendarList, Meeting meeting) {
        for (Calendar calendar : calendarList) {
            sendMessageToOneRecipientAndSaveEnrollment(calendar, meeting);

        }
        logger.info("Messages to all recipients were sended successfully");
        return new CalendarSendingResponse(true, "All messages was sended successfully");
    }
}
