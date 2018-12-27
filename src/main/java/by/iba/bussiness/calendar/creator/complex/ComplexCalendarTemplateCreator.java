package by.iba.bussiness.calendar.creator.complex;

import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.calendar.date.model.complex.ComplexDateHelper;
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
import java.util.List;

@Component
public class ComplexCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(ComplexCalendarTemplateCreator.class);
    private CalendarTextEditor calendarTextEditor;
    private Calendar publishCalendar;
    private MeetingService meetingService;

    @Autowired
    public ComplexCalendarTemplateCreator(CalendarTextEditor calendarTextEditor,
                                          @Qualifier("publishCalendar") Calendar publishCalendar,
                                          MeetingService meetingService) {
        this.calendarTextEditor = calendarTextEditor;
        this.publishCalendar = publishCalendar;
        this.meetingService = meetingService;
    }

    public Calendar createComplexCalendarInvitationTemplate(ComplexDateHelper complexDateHelper, Meeting meeting) {
        logger.info("Started creating ics file with complex meeting with id " + meeting.getId());
        List<Session> sessionList = complexDateHelper.getSessionList();
        String summary = calendarTextEditor.breakLine(meeting.getSummary());
        String description = calendarTextEditor.breakLine(meeting.getDescription());
        String location = calendarTextEditor.breakLine(meeting.getLocation());
        String sequence = "0";

        Calendar calendar = null;
        try {
            calendar = new Calendar(publishCalendar);
        } catch (ParseException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        for (Session session : sessionList) {
            DateTime startDateTime = new DateTime(session.getStartDate());
            DateTime endDateTime = new DateTime(session.getEndDate());
            calendar.getComponents().add(new VEvent(startDateTime, endDateTime, summary));

            CalendarComponent event = calendar.getComponents().getComponent(CalendarComponent.VEVENT);
            event.getProperties().add(new Sequence(sequence));
            event.getProperties().add(new Location(location));
            event.getProperties().add(new Description(description));

            FixedUidGenerator fixedUidGenerator;
            try {
                event.getProperties().add(new Organizer("mailto:" + meeting.getOwner().getEmail()));
                fixedUidGenerator = new FixedUidGenerator("YourLearning.Complex");
            } catch (URISyntaxException | SocketException e) {
                logger.error(e.getMessage());
                throw new CalendarException("Can't create calendar meeting. Try againg later");
            }
            Uid UID = fixedUidGenerator.generateUid();
            event.getProperties().add(UID);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return calendar;
    }
}
