package by.iba.bussines.calendar.creator;

import by.iba.bussines.calendar.attendee.Attendee;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Rsvp;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalendarListCreator {
    public List<Calendar> createCalendarList(List<Attendee> attendeeList, Calendar calendar) {
        List<Calendar> calendarList = new ArrayList<>(attendeeList.size());
        for (Attendee attendee : attendeeList) {
            net.fortuna.ical4j.model.property.Attendee listener = new net.fortuna.ical4j.model.property.Attendee(URI.create(attendee.getEmail()));
            listener.getParameters().add(new Cn(attendee.getCommonName()));
            listener.getParameters().add(Rsvp.FALSE);

            net.fortuna.ical4j.model.Component vEvent = calendar.getComponent(net.fortuna.ical4j.model.Component.VEVENT);
            vEvent.getProperties().add(listener);

            calendarList.add(calendar);
        }
        if (calendarList.isEmpty()) {
            throw new NullPointerException("Your calendar list is empty!");
        }
        return calendarList;
    }
}
