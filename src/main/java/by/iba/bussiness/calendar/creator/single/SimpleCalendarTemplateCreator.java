package by.iba.bussiness.calendar.creator.single;

import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.calendar.date.model.single.SingleDateHelper;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.meeting.Meeting;
import by.iba.exception.CalendarException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.UUID;

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
        Calendar calendar;
        VEvent event;
        try {
            Sequence sequence = new Sequence("0");
            Organizer organizer = new Organizer("mailto:" + meeting.getOwner().getEmail());
            Location location = new Location(calendarTextEditor.breakLine(meeting.getLocation()));
            Description description = new Description(calendarTextEditor.breakLine(meeting.getDescription()));
            Summary summary = new Summary(calendarTextEditor.breakLine(meeting.getSummary()));

            Uid UID = new Uid(UUID.randomUUID().toString());

            Session session = singleDateHelper.getSession();
            DateTime startDateTime = new DateTime(session.getStartDate());
            DateTime endDateTime = new DateTime(session.getEndDate());

            calendar = new Calendar(requestCalendar);
            event = new VEvent(startDateTime, endDateTime, summary.toString());
            event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, summary, UID));
            calendar.getComponents().add(event);
        } catch (ParseException | URISyntaxException | IOException e) {
            logger.error(e.getMessage());
            throw new CalendarException("Can't create single calendar meeting. Try again later");
        }
        return calendar;
    }
}
