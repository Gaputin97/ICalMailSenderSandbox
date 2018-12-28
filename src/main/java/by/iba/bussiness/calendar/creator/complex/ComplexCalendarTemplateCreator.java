package by.iba.bussiness.calendar.creator.complex;

import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.calendar.date.model.complex.ComplexDateHelper;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class ComplexCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(ComplexCalendarTemplateCreator.class);
    private CalendarTextEditor calendarTextEditor;
    private Calendar publishCalendar;

    @Autowired
    public ComplexCalendarTemplateCreator(CalendarTextEditor calendarTextEditor,
                                          @Qualifier("publishCalendar") Calendar publishCalendar) {
        this.calendarTextEditor = calendarTextEditor;
        this.publishCalendar = publishCalendar;
    }

    public Calendar createComplexCalendarInvitationTemplate(ComplexDateHelper complexDateHelper, Meeting meeting) {
        List<Session> sessionList = complexDateHelper.getSessionList();
        Calendar calendar = null;
        VEvent event;
        for (Session session : sessionList) {
            try {
                Sequence sequence = new Sequence("0");
                Organizer organizer = new Organizer("mailto:" + meeting.getOwner().getEmail());
                Location location = new Location(calendarTextEditor.breakLine(meeting.getLocation()));
                Description description = new Description(calendarTextEditor.breakLine(meeting.getDescription()));
                Summary summary = new Summary(calendarTextEditor.breakLine(meeting.getSummary()));

                FixedUidGenerator fixedUidGenerator = new FixedUidGenerator("YourLearning");
                Uid uid = fixedUidGenerator.generateUid();

            try {
                event.getProperties().add(new Organizer("mailto:" + meeting.getOwner().getEmail()));
            } catch (URISyntaxException | SocketException e) {
                logger.error("Can't create calendar template", e);
                throw new CalendarException("Can't create calendar meeting. Try again later");
            }
            Uid UID = new Uid(UUID.randomUUID().toString());
            event.getProperties().add(UID);

                DateTime startDateTime = new DateTime(session.getStartDate());
                DateTime endDateTime = new DateTime(session.getEndDate());

                calendar = new Calendar(publishCalendar);
                event = new VEvent(startDateTime, endDateTime, summary.toString());
                event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, summary, uid));
                calendar.getComponents().add(event);
            } catch (ParseException | URISyntaxException | IOException e) {
                logger.error(e.getMessage());
                throw new CalendarException("Can't create complex calendar meeting. Try again later");
            }
        }
        return calendar;
    }
}
