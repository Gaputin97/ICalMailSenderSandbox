package by.iba.bussines.sender.service.v1;

import by.iba.bussines.sender.service.InstallationEventSender;
import by.iba.bussines.sender.service.method.Method;
import by.iba.bussines.status.send.CalendarSendingStatus;
import by.iba.exception.CalendarSendingException;
import net.fortuna.ical4j.model.Calendar;
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
    public CalendarSendingStatus sendCalendarToRecipients(String[] recipientList, Calendar calendar, Method method) {
        MimeMessage message;
        try {
            message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientList);

            MimeMultipart multipart = new MimeMultipart("text/calendar");

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

            message.setContent(multipart);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new CalendarSendingException("Exception with send calendar: " + calendar.toString());
        }
        return new CalendarSendingStatus("Calendar message was successfully sanded");
    }
}
