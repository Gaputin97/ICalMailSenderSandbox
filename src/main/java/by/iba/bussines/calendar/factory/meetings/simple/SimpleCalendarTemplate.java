package by.iba.bussines.calendar.factory.meetings.simple;

import by.iba.bussines.calendar.factory.text.preparing.CalendarTextBreaker;
import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.calendar.factory.type.MeetingType;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.FixedUidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;

@Component
public class SimpleCalendarTemplate {
    private static final String PRODUCT_IDENTIFIER = "-//Your Learning//EN";

    @Autowired
    CalendarTextBreaker calendarTextBreaker;

    public Calendar createSimpleMeetingCalendarTemplate(Meeting meeting, MeetingType method) {
        Calendar singleMeetingCalendar = new Calendar();
        singleMeetingCalendar.getProperties().add(Version.VERSION_2_0);
        singleMeetingCalendar.getProperties().add(CalScale.GREGORIAN);
        singleMeetingCalendar.getProperties().add(new ProdId(PRODUCT_IDENTIFIER));
        singleMeetingCalendar.getProperties().add(method.getMethod());

        DateTime startDateTime = null;
        DateTime endDateTime = null;
        try {
            startDateTime = new DateTime(meeting.getStartDateTime());
            endDateTime = new DateTime(meeting.getEndDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        VEvent event = new VEvent(startDateTime, endDateTime, meeting.getSummary());

        event.getProperties().add(new Sequence("0")); //HAAARDCODE, remake after adding appointment
        event.getProperties().add(new Location(calendarTextBreaker.lineBreakAndGet(meeting.getLocation())));
        event.getProperties().add(new Description(calendarTextBreaker.lineBreakAndGet(meeting.getDescription())));

        FixedUidGenerator fixedUidGenerator = null;
        try {
            event.getProperties().add(new Organizer("mailto:" + meeting.getOwner().getEmail()));
            fixedUidGenerator = new FixedUidGenerator("YourLearning");
        } catch (URISyntaxException | SocketException e) {
            e.printStackTrace();
        }

        // Remake after enter appointment
        Uid UID = fixedUidGenerator.generateUid();
        event.getProperties().add(UID);
        singleMeetingCalendar.getComponents().add(event);

        return singleMeetingCalendar;
    }
}
