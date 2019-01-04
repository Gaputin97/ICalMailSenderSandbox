package by.iba.bussiness.calendar.creator.single;

import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.calendar.creator.UidDefiner;
import by.iba.bussiness.calendar.date.model.single.SingleDateHelper;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.invitation_template.service.InvitationTemplateService;
import by.iba.bussiness.meeting.Meeting;
import by.iba.exception.CalendarException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;

@Component

public class SimpleMeetingCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(SimpleMeetingCalendarTemplateCreator.class);
    private CalendarTextEditor calendarTextEditor;
    private Calendar requestCalendar;
    private Calendar cancelCalendar;
    private UidDefiner uidDefiner;
    private InvitationTemplateService invitationTemplateService;

    @Autowired
    public SimpleMeetingCalendarTemplateCreator(CalendarTextEditor calendarTextEditor,
                                                @Qualifier("requestCalendar") Calendar requestCalendar,
                                                @Qualifier("cancelCalendar") Calendar cancelCalendar,
                                                UidDefiner uidDefiner,
                                                InvitationTemplateService invitationTemplateService) {
        this.calendarTextEditor = calendarTextEditor;
        this.requestCalendar = requestCalendar;
        this.cancelCalendar = cancelCalendar;
        this.uidDefiner = uidDefiner;
        this.invitationTemplateService = invitationTemplateService;
    }

    public Calendar createSimpleMeetingInvitationTemplate(SingleDateHelper singleDateHelper, Meeting meeting, String calendarUid) {
        logger.info("Started creating invitation ics file with single meeting with id " + meeting.getId());
        Calendar calendar;
        VEvent event;
        try {
            Sequence sequence = new Sequence("0");
            Organizer organizer = new Organizer("mailto:" + meeting.getOwner().getEmail());
            Location location = new Location(meeting.getLocation());
            Description description = new Description((meeting.getDescription()));
            Summary summary = new Summary((meeting.getSummary()));
            Uid UID = uidDefiner.defineUid(calendarUid);
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

    public Calendar createSimpleMeetingCancellationTemplate(SingleDateHelper singleDateHelper, Meeting meeting, String calendarUid) {
        logger.info("Started creating cancellation ics file with single meeting with id " + meeting.getId());
        Calendar calendar;
        VEvent event;
        try {
            Sequence sequence = new Sequence("0");
            Organizer organizer = new Organizer("mailto:" + meeting.getOwner().getEmail());
            Location location = new Location((meeting.getLocation()));
            Description description = new Description((meeting.getDescription()));
            Summary summary = new Summary((meeting.getSummary()));
            Uid UID = uidDefiner.defineUid(calendarUid);
            Session session = singleDateHelper.getSession();
            DateTime startDateTime = new DateTime(session.getStartDate());
            DateTime endDateTime = new DateTime(session.getEndDate());

            calendar = new Calendar(cancelCalendar);
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
