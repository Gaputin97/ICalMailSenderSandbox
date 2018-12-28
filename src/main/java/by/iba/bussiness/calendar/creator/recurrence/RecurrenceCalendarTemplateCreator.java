package by.iba.bussiness.calendar.creator.recurrence;

import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.calendar.date.DateHelperConstants;
import by.iba.bussiness.calendar.date.model.reccurence.RecurrenceDateHelper;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.exception.CalendarException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.FixedUidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Component
public class RecurrenceCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(RecurrenceCalendarTemplateCreator.class);
    private CalendarTextEditor calendarTextEditor;
    private Calendar requestCalendar;
    private SessionParser sessionParser;
    private DateHelperConstants dateHelperConstants;
    private IcalDateParser iСalDateParser;
    private DateIncreaser dateIncreaser;

    @Autowired
    public RecurrenceCalendarTemplateCreator(CalendarTextEditor calendarTextEditor,
                                             @Qualifier("requestCalendar") Calendar requestCalendar,
                                             SessionParser sessionParser,
                                             DateHelperConstants dateHelperConstants,
                                             IcalDateParser icalDateParser,
                                             DateIncreaser dateIncreaser) {
        this.calendarTextEditor = calendarTextEditor;
        this.requestCalendar = requestCalendar;
        this.sessionParser = sessionParser;
        this.dateHelperConstants = dateHelperConstants;
        this.iСalDateParser = icalDateParser;
        this.dateIncreaser = dateIncreaser;
    }

    public Calendar createRecurrenceCalendarInvitationTemplate(RecurrenceDateHelper recurrenceDateHelper, Meeting meeting) {
        Rrule rrule = recurrenceDateHelper.getRrule();
        DateList exDatesList = new DateList();
        rrule.getExDates().forEach(x -> exDatesList.add(new Date(x)));

        List<TimeSlot> meetingTimeSlots = meeting.getTimeSlots();
        TimeSlot firstTimeSlot = meetingTimeSlots.get(dateHelperConstants.getNumberOfFirstTimeSlot());
        TimeSlot lastTimeSlot = meetingTimeSlots.get(meetingTimeSlots.size() - 1);

        Session lastSession = sessionParser.timeSlotToSession(lastTimeSlot);
        String increasedDate = dateIncreaser.increaseAndParse(rrule.getFrequency(), rrule.getInterval(), lastSession.getStartDate());

        Session firstSession = sessionParser.timeSlotToSession(firstTimeSlot);

        Calendar calendar;
        VEvent event;
        try {
            Sequence sequence = new Sequence("0");
            Organizer organizer = new Organizer("mailto:" + meeting.getOwner().getEmail());
            Location location = new Location(calendarTextEditor.breakLine(meeting.getLocation()));
            Description description = new Description(calendarTextEditor.breakLine(meeting.getDescription()));
            Summary summary = new Summary(meeting.getSummary());
            String frequency = rrule.getFrequency().toString();
            Long interval = rrule.getInterval();
            String until = iСalDateParser.parseToICalDate(increasedDate);
            ExDate exDates = new ExDate(exDatesList);

            Recur recurrence = new Recur("FREQ=" + frequency + ";" + "INTERVAL=" + interval + ";" + "UNTIL=" + until + ";");
            RRule rRule = new RRule(recurrence);

            FixedUidGenerator fixedUidGenerator = new FixedUidGenerator("YourLearning");
            Uid uid = fixedUidGenerator.generateUid();

            DateTime startDateTime = new DateTime(firstSession.getStartDate());
            DateTime endDateTime = new DateTime(firstSession.getEndDate());

            calendar = new Calendar(requestCalendar);
            event = new VEvent(startDateTime, endDateTime, summary.toString());
            event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, uid, rRule, exDates));
            calendar.getComponents().add(event);
        } catch (ParseException | URISyntaxException | IOException e) {
            logger.error(e.getMessage());
            throw new CalendarException("Can't create recurrence calendar meeting. Try again later");
        }
        return calendar;
    }
}
