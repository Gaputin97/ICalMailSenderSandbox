package by.iba.bussiness.calendar.creator.single;

import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.calendar.date.model.single.SingleDateHelper;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.calendar.session.Session;
import by.iba.exception.CalendarException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.FixedUidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;

@Component

public class SimpleCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCalendarTemplateCreator.class);
    private CalendarTextEditor calendarTextEditor;
    private Calendar requestCalendar;

    @Autowired
    public SimpleCalendarTemplateCreator(CalendarTextEditor calendarTextEditor,
                                         Calendar requestCalendar) {
        this.calendarTextEditor = calendarTextEditor;
        this.requestCalendar = requestCalendar;
    }

    public Calendar createSimpleMeetingInvitationTemplate(SingleDateHelper singleDateHelper, Meeting meeting) {
        logger.info("Started creating ics file with single meeting with id " + meeting.getId());
        Session session = singleDateHelper.getSession();
        DateTime startDateTime = new DateTime(session.getStartDate());
        DateTime endDateTime = new DateTime(session.getEndDate());

        String summary = calendarTextEditor.breakLine(meeting.getSummary());

        Calendar calendar;
        CalendarComponent event;
        FixedUidGenerator fixedUidGenerator;
        try {
            calendar = new Calendar(requestCalendar);
            calendar.getComponents().add(new VEvent(startDateTime, endDateTime, summary));
            event = calendar.getComponents().getComponent(CalendarComponent.VEVENT);

            event.getProperties().add(new Organizer("mailto:" + meeting.getOwner().getEmail()));
            fixedUidGenerator = new FixedUidGenerator("YourLearning");
        } catch (ParseException | IOException | URISyntaxException e) {
            logger.error("Can't create calendar: " + e.getStackTrace());
            throw new CalendarException("Can't create calendar meeting. Try again later");
        }

        Uid UID = fixedUidGenerator.generateUid();
        event.getProperties().add(UID);

        event.getProperties().add(new Sequence("0"));
        event.getProperties().add(new Location(calendarTextEditor.breakLine(meeting.getLocation())));
        event.getProperties().add(new Description(calendarTextEditor.breakLine(meeting.getDescription())));

        return calendar;
    }
}
