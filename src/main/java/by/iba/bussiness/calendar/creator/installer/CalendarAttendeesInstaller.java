package by.iba.bussiness.calendar.creator.installer;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.CalendarException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.Attendee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;

@org.springframework.stereotype.Component
public class CalendarAttendeesInstaller {
    private static final Logger logger = LoggerFactory.getLogger(CalendarAttendeesInstaller.class);

    public Calendar installAttendeeToCalendar(String userEmail, Calendar preInstalledCalendar) {
        Calendar calendarWithAttendee;
        try {
            calendarWithAttendee = new Calendar(preInstalledCalendar);
            CalendarComponent vEvent = calendarWithAttendee.getComponent(Component.VEVENT);
            Attendee attendee = new Attendee(URI.create(userEmail));
            attendee.getParameters().add(Rsvp.TRUE);
            attendee.getParameters().add(Role.REQ_PARTICIPANT);
            vEvent.getProperties().add(attendee);
            return calendarWithAttendee;
        } catch (ParseException | URISyntaxException | IOException e) {
            logger.error("Can't create calendar based on another calendar", e);
            throw new CalendarException("Can't create calendar.");
        }
    }
}