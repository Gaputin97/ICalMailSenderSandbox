package by.iba.bussiness.calendar.creator;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.parameter.Rsvp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalendarListCreator {
    Logger logger = LoggerFactory.getLogger(CalendarListCreator.class);
    public List<Calendar> createCalendarList(String attendeeList, Calendar calendar) {
        List<Calendar> calendarList = new ArrayList<>();
//        for (String attendee : attendeeList) {
            logger.info("Attendee: " + attendeeList);
            net.fortuna.ical4j.model.property.Attendee listener = new net.fortuna.ical4j.model.property.Attendee(URI.create(attendeeList));

            listener.getParameters().add(Rsvp.FALSE);

            net.fortuna.ical4j.model.Component vEvent = calendar.getComponent(net.fortuna.ical4j.model.Component.VEVENT);
            vEvent.getProperties().add(listener);

            calendarList.add(calendar);
//        }
        if (calendarList.isEmpty()) {
            throw new NullPointerException("Your calendar list is empty!");
        }
        return calendarList;
    }
}
