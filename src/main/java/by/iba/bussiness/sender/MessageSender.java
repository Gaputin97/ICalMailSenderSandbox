package by.iba.bussiness.sender;

import by.iba.bussiness.calendar.creator.text_preparing.CalendarTextEditor;
import by.iba.bussiness.enrollment.model.Enrollment;
import by.iba.bussiness.enrollment.service.v1.EnrollmentServiceImpl;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.status.send.CalendarSendingStatus;
import by.iba.exception.SendingException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.Attendee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;

@org.springframework.stereotype.Component
public class MessageSender {

    private Logger logger = LoggerFactory.getLogger(MessageSender.class);
    private JavaMailSender javaMailSender;
    private CalendarTextEditor calendarTextEditor;
    private EnrollmentServiceImpl enrollmentService;

    @Autowired
    public MessageSender(JavaMailSender javaMailSender, CalendarTextEditor calendarTextEditor, EnrollmentServiceImpl enrollmentService) {
        this.javaMailSender = javaMailSender;
        this.calendarTextEditor = calendarTextEditor;
        this.enrollmentService = enrollmentService;
    }

    public void sendMessageToOneRecipient(Calendar calendar, Meeting meeting) {
        MimeMessage message;
        try {
            message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Component event = calendar.getComponents().getComponent(Component.VEVENT);
            Property attendee = event.getProperties().getProperty(Property.ATTENDEE);
            String address = ((Attendee) attendee).getCalAddress().toString();
            String method = calendarTextEditor.replaceColonToEqual(calendar.getMethod().toString());
            String editedUserEmail = calendarTextEditor.editUserEmail(address);
            helper.setTo(editedUserEmail);

            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart iСalInline = new MimeBodyPart();
            iСalInline.setHeader("Content-class", "urn:content-classes:calendarmessage");
            iСalInline.setHeader("Content-ID", "<calendar_part>");
            iСalInline.setHeader("Content-Disposition", "inline");
            iСalInline.setContent(calendar.toString(), "text/calendar;charset=utf-8;" + method);
            iСalInline.setFileName("inlineCalendar.ics");
            multipart.addBodyPart(iСalInline);

            MimeBodyPart iСalAttachment = new MimeBodyPart();
            iСalAttachment.setHeader("Content-class", "urn:content-classes:calendarmessage");
            iСalAttachment.setHeader("Content-Disposition", "attachment");
            iСalAttachment.setContent(calendar.toString(), "text/calendar;charset=utf-8;" + method);
            iСalAttachment.setFileName("attachedCalendar.ics");
            multipart.addBodyPart(iСalAttachment);
            Enrollment enrollment = new Enrollment();
            enrollment.setParentId(meeting.getId());
            enrollment.setUserEmail(editedUserEmail);
            message.setContent(multipart);
            javaMailSender.send(message);
            logger.info("Message was sended to " + editedUserEmail);
            enrollmentService.saveEnrollment(enrollment);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
            throw new SendingException("Error while trying to send message.");
        }
    }

    public CalendarSendingStatus sendMessageToAllRecipients(List<Calendar> calendarList, Meeting meeting) {
        for (Calendar calendar : calendarList) {
            sendMessageToOneRecipient(calendar, meeting);
        }
        logger.info("Messages to all recipients were sended successfully");
        return new CalendarSendingStatus("Messages with calendar were sended to all recipients");
    }
}
