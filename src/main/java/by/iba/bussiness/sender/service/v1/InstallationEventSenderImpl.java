package by.iba.bussines.sender.service.v1;

import by.iba.bussines.sender.service.InstallationEventSender;
import by.iba.bussines.status.send.CalendarSendingStatus;
import by.iba.exception.CalendarSendingException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.Attendee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Service
public class InstallationEventSenderImpl implements InstallationEventSender {
    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public CalendarSendingStatus sendCalendarToRecipient(Calendar calendar) {
        MimeMessage message;
        try {
            message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Component event = calendar.getComponents().getComponent(Component.VEVENT);
            Property attendee = event.getProperties().getProperty(Property.ATTENDEE);
            helper.setTo(((Attendee) attendee).getCalAddress().toString());

            MimeMultipart multipart = new MimeMultipart("text/calendar");

            MimeBodyPart iСalInline = new MimeBodyPart();
            iСalInline.setHeader("Content-class", "urn:content-classes:calendarmessage");
            iСalInline.setHeader("Content-ID", "<calendar_part>");
            iСalInline.setHeader("Content-Disposition", "inline");
            iСalInline.setContent(calendar.toString(), "text/calendar;charset=utf-8;" + calendar.getMethod());
            iСalInline.setFileName("inlineCalendar.ics");
            multipart.addBodyPart(iСalInline);

            MimeBodyPart iСalAttachment = new MimeBodyPart();
            iСalAttachment.setHeader("Content-class", "urn:content-classes:calendarmessage");
            iСalAttachment.setHeader("Content-Disposition", "attachment");
            iСalAttachment.setContent(calendar.toString(), "text/calendar;charset=utf-8;" + calendar.getMethod());
            iСalAttachment.setFileName("attachedCalendar.ics");
            multipart.addBodyPart(iСalAttachment);

            message.setContent(multipart);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new CalendarSendingException("Exception with send calendar: " + e.getMessage());
        }
        return new CalendarSendingStatus("Calendar message was successfully sanded");
    }
}
