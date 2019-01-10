package by.iba.bussiness.calendar.creator.complex;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.calendar.date.model.complex.ComplexDateHelper;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.exception.CalendarException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@Component
public class ComplexMeetingCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(ComplexMeetingCalendarTemplateCreator.class);
    private Calendar publishCalendar;

    @Autowired
    public ComplexMeetingCalendarTemplateCreator(@Qualifier("publishCalendar") Calendar publishCalendar) {
        this.publishCalendar = publishCalendar;
    }

    public Calendar createComplexCalendarInvitationTemplate(ComplexDateHelper complexDateHelper, Appointment appointment, Enrollment enrollment) {
        List<Session> sessionList = complexDateHelper.getSessionList();
        Calendar calendar = null;
        VEvent event;
        for (Session session : sessionList) {
            try {
                Sequence sequence = new Sequence("0");
                Organizer organizer = new Organizer("mailto:" + appointment.getOwner().getEmail());
                Location location = new Location((appointment.getLocation()));
                Description description = new Description((appointment.getDescription()));
                Summary summary = new Summary((appointment.getSummary()));
                Uid UID = new Uid(enrollment.getCurrentCalendarUid());

                DateTime startDateTime = new DateTime(session.getStartDate());
                DateTime endDateTime = new DateTime(session.getEndDate());

                calendar = new Calendar(publishCalendar);
                event = new VEvent(startDateTime, endDateTime, summary.toString());
                event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, summary, UID));
                calendar.getComponents().add(event);
            } catch (ParseException | URISyntaxException | IOException e) {
                logger.error(e.getMessage());
                throw new CalendarException("Can't create complex calendar meeting. Try again later");
            }
        }
        return calendar;
    }
}