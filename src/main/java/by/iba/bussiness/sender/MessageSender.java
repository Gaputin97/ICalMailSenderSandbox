package by.iba.bussiness.sender;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.CalendarTextEditor;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.notification.NotificationResponseStatus;
import by.iba.bussiness.template.Template;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
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
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.net.URL;

@org.springframework.stereotype.Component
public class MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
    private JavaMailSender javaMailSender;
    private CalendarTextEditor calendarTextEditor;
    private Configuration freeMarkerConfiguration;
    private static final String BODY_OPEN_TAG = "<body>";
    private static final String BODY_CLOSE_TAG = "</body>";

    @Autowired
    public MessageSender(JavaMailSender javaMailSender,
                         CalendarTextEditor calendarTextEditor,
                         Configuration freeMarkerConfiguration) {
        this.javaMailSender = javaMailSender;
        this.calendarTextEditor = calendarTextEditor;
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }

    public NotificationResponseStatus sendCalendar(Calendar calendar,
                                                   String enrollmentCalendarStatus,
                                                   Appointment appointment) {
        MimeMessage message;
        VEvent event = (VEvent) calendar.getComponents().getComponent(Component.VEVENT);
        Attendee attendee = event.getProperties().getProperty(Property.ATTENDEE);
        String userEmail = attendee.getCalAddress().toString();
        String meetingTitle = appointment.getTitle();
        String ownerMail = appointment.getFrom();
        String ownerName = appointment.getFromName();
        Meeting

        String richDescription = BODY_OPEN_TAG + appointment.getDescription() + BODY_CLOSE_TAG;
        NotificationResponseStatus notificationResponseStatus;
        try {
            message = javaMailSender.createMimeMessage();
            message.setSubject(meetingTitle + " : " + enrollmentCalendarStatus);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            Method method = calendar.getMethod();
            String stringMethod = calendarTextEditor.replaceColonToEqual(method.toString());
            InternetAddress address = new InternetAddress(ownerMail, ownerName);
            helper.setFrom(address);
            helper.setTo(userEmail);
            MimeBodyPart iCalInline = new MimeBodyPart();
            iCalInline.setHeader("Content-ID", "calendar_part");
            iCalInline.setHeader("Content-Disposition", "inline");
            iCalInline.setHeader("Content-Transfer-Encoding", "base64");
            iCalInline.setContent(calendar.toString(), "text/calendar;charset=utf-8;" + stringMethod);
            iCalInline.setFileName("inlineCalendar.ics");

            MimeBodyPart htmlInline = new MimeBodyPart();
            htmlInline.setHeader("Content-ID", "rich_description");
            htmlInline.setHeader("Content-Disposition", "inline");
            htmlInline.setHeader("Content-Transfer-Encoding", "base64");
            htmlInline.setContent(richDescription, "text/html;charset=utf-8");

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlInline);
            multipart.addBodyPart(iCalInline);
            message.setContent(multipart);
            javaMailSender.send(message);

            logger.info("Message was sent to " + userEmail);
            notificationResponseStatus = new NotificationResponseStatus(true, "Calendar was sent successfully", userEmail);
        } catch (MessagingException | IOException e) {
            logger.error("Error while trying to send a message", e);
            notificationResponseStatus = new NotificationResponseStatus(false, "Calendar was not delivered", userEmail);
        }
        return notificationResponseStatus;
    }

    public NotificationResponseStatus sendTemplate(Template template, String userEmail, String meetingTitle) {
        MimeMessage message;
        NotificationResponseStatus notificationResponseStatus;
        String from = template.getFrom();
        String fromName = template.getFromName();
        try {
            message = javaMailSender.createMimeMessage();
            message.setSubject(meetingTitle + " : " + template.getType());
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    true,
                    "utf-8");
            URL url = new URL("https://preview.ibb.co/hXyhQL/Meeting.jpg");
            helper.setTo(userEmail);
            InternetAddress address = new InternetAddress(from, fromName);
            helper.setFrom(address);
            freemarker.template.Template messageTemplate = freeMarkerConfiguration.getTemplate("message.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(messageTemplate, template);
            Multipart multipart = new MimeMultipart();
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(html, "text/html; charset=UTF-8");

            MimeBodyPart inlineImage = new MimeBodyPart();
            inlineImage.setContentID("<pic>");
            inlineImage.setHeader("Content-Disposition", "inline");
            inlineImage.setDataHandler(new DataHandler(url));

            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(inlineImage);
            message.setContent(multipart);

            javaMailSender.send(message);
            logger.info("Message was sent to " + userEmail);
            notificationResponseStatus = new NotificationResponseStatus(true, "Message was sent successfully", userEmail);
        } catch (MessagingException | TemplateException | IOException e) {
            logger.error("Error while trying to send message", e);
            notificationResponseStatus = new NotificationResponseStatus(false, "Message was not delivered", userEmail);
        }
        return notificationResponseStatus;
    }
}