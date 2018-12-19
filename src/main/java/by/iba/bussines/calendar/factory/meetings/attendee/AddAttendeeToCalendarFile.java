package by.iba.bussines.calendar.factory.meetings.attendee;

import by.iba.bussines.calendar.factory.model.AttendeeModel;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.property.Attendee;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AddAttendeeToCalendarFile {
    public Map<@Email String, Calendar> createCalendarForEachAttendee(List<AttendeeModel> attendeeList, Calendar calendar) {
        Map<String, Calendar> calendarMap = new HashMap<>();
        for (AttendeeModel attendee : attendeeList) {
            Attendee listener = new Attendee(URI.create(attendee.getEmail()));
            listener.getParameters().add(new Cn(attendee.getCommonName()));
            listener.getParameters().add(Rsvp.FALSE);

            net.fortuna.ical4j.model.Component vEvent = calendar.getComponent(net.fortuna.ical4j.model.Component.VEVENT);
            vEvent.getProperties().add(listener);

            calendarMap.put(attendee.getEmail(), calendar);
        }
        if(calendarMap.isEmpty()) {
            throw new NullPointerException("Your map is empty!");
        }
        return calendarMap;
    }
}
