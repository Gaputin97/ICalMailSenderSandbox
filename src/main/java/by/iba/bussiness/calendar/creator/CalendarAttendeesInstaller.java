package by.iba.bussiness.calendar.creator;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.CalendarException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.Attendee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Component
public class CalendarAttendeesInstaller {
    private static final Logger logger = LoggerFactory.getLogger(CalendarAttendeesInstaller.class);

    public List<Calendar> createCalendarList(List<String> emails, Calendar calendar) {
        List<Calendar> calendarList = new ArrayList<>();

        for (String email : emails) {
            Attendee listener = new Attendee(URI.create("mailto:" + email));
            listener.getParameters().add(Rsvp.FALSE);
            listener.getParameters().add(Role.REQ_PARTICIPANT);
            listener.getParameters().add(PartStat.ACCEPTED);

            CalendarComponent event = calendar.getComponent(Component.VEVENT);
            event.getProperties().add(listener);

            try {
                calendarList.add(new Calendar(calendar));
            } catch (ParseException | IOException | URISyntaxException e) {
                logger.error("Can't create calendar list", e);
                throw new CalendarException("Can't create calendar meeting. Try again later.");
            }
            event.getProperties().remove(listener);
        }
        return calendarList;
    }
}
