package by.iba.bussines.calendar.factory.single;

import by.iba.bussines.meeting.model.Meeting;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.FixedUidGenerator;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;

@Component
public class SingleCalendarTemplate {
    private static final String PRODUCT_IDENTIFIER = "-//Your Learning//EN";

    public Calendar createSingleCalendar(Meeting meeting, Method method) {

        Calendar singleMeetingCalendar = new Calendar();
        singleMeetingCalendar.getProperties().add(Version.VERSION_2_0);
        singleMeetingCalendar.getProperties().add(CalScale.GREGORIAN);
        singleMeetingCalendar.getProperties().add(new ProdId(PRODUCT_IDENTIFIER));
        singleMeetingCalendar.getProperties().add(method);

        DateTime startDateTime = null;
        DateTime endDateTime = null;

        try {
            startDateTime = new DateTime(meeting.getStartDateTime());
            endDateTime = new DateTime(meeting.getEndDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        VEvent event = new VEvent(startDateTime, endDateTime, meeting.getSummary());
        event.getProperties().add(new Description);
        event.getProperties().add(new Sequence("0"));
        PropertyList<Property> eventProperties = getEventProperties();
        event.getProperties().addAll(eventProperties);

        XProperty lotusNotesType = new XProperty("X-LOTUS-NOTICETYPE", "I");
        event.getProperties().add(lotusNotesType);

        FixedUidGenerator fixedUidGenerator = null;
        try {
            event.getProperties().add(new Organizer("mailto:gaputinseva@gmail.com"));
            event.getProperties().addAll(getAttendeeList());
            fixedUidGenerator = new FixedUidGenerator("UHaputsin");
        } catch (URISyntaxException | SocketException e) {
            e.printStackTrace();
        }

        UID = fixedUidGenerator.generateUid();
        event.getProperties().add(UID);

        simpleEventInvitation.getComponents().add(event);
        simpleEventInvitation.validate();
        logger.info("\nCalendar: \n" + simpleEventInvitation.toString());
        return simpleEventInvitation;
    }
}
