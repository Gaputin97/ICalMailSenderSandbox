package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.calendar.email.Email;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.CalendarException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.CalendarComponent;
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
public class CalendarListCreator {
    private Logger logger = LoggerFactory.getLogger(CalendarListCreator.class);

    public List<Calendar> createCalendarList(Email emailList, Calendar calendar) {
        List<Calendar> calendarList = new ArrayList<>();

        for (String email : emailList.getEmails()) {
            Attendee listener = new Attendee(URI.create("mailto:" + email));
            listener.getParameters().add(Rsvp.FALSE);

            CalendarComponent event = calendar.getComponent(Component.VEVENT);
            event.getProperties().add(listener);

            try {
                calendarList.add(new Calendar(calendar));
            } catch (ParseException | IOException | URISyntaxException ex) {
                logger.info(ex.getMessage());
                throw new CalendarException("Can't create calendar meeting. Try again later.");
            }
            event.getProperties().remove(listener);
        }
        if (calendarList.isEmpty()) {
            throw new NullPointerException("Your calendar list is empty!");
        }
        return calendarList;
    }
}
