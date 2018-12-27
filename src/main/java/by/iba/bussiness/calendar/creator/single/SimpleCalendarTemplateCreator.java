package by.iba.bussiness.calendar.creator.type.single;

import by.iba.bussiness.calendar.creator.text_preparing.CalendarTextEditor;
import by.iba.bussiness.calendar.date.model.single.SingleDateHelper;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.calendar.session.model.Session;
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
    private Logger logger = LoggerFactory.getLogger(SimpleCalendarTemplateCreator.class);
    private CalendarTextEditor calendarTextEditor;
    private Calendar requestCalendar;

    @Autowired
    public SimpleCalendarTemplateCreator(CalendarTextEditor calendarTextEditor,
                                         @Qualifier("requestCalendar") Calendar requestCalendar) {
        this.calendarTextEditor = calendarTextEditor;
        this.requestCalendar = requestCalendar;
    }

    public Calendar createSimpleMeetingInvitationTemplate(SingleDateHelper singleDateHelper, Meeting meeting) {
        logger.info("Started creating ics file with single meeting with id " + meeting.getId());
        Session session = singleDateHelper.getSession();
        DateTime startDateTime = new DateTime(session.getStartDate());
        DateTime endDateTime = new DateTime(session.getEndDate());

        String summary = calendarTextEditor.breakLine(meeting.getSummary());

        Calendar calendar = null;
        try {
            calendar = new Calendar(requestCalendar);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        calendar.getComponents().add(new VEvent(startDateTime, endDateTime, summary));
        CalendarComponent event = calendar.getComponents().getComponent(CalendarComponent.VEVENT);

        event.getProperties().add(new Sequence("0"));
        event.getProperties().add(new Location(calendarTextEditor.breakLine(meeting.getLocation())));
        event.getProperties().add(new Description(calendarTextEditor.breakLine(meeting.getDescription())));

        FixedUidGenerator fixedUidGenerator;
        try {
            event.getProperties().add(new Organizer("mailto:" + meeting.getOwner().getEmail()));
            fixedUidGenerator = new FixedUidGenerator("YourLearning");
        } catch (URISyntaxException | SocketException e) {
            logger.error(e.getMessage());
            throw new CalendarException("Can't create calendar meeting. Try againg later");
        }
        Uid UID = fixedUidGenerator.generateUid();
        event.getProperties().add(UID);

        return calendar;
    }
}
