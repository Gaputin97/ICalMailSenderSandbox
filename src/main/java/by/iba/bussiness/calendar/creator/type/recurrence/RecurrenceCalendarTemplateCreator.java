package by.iba.bussiness.calendar.creator.type.recurrence;

import by.iba.bussiness.calendar.creator.text_preparing.CalendarTextEditor;
import by.iba.bussiness.calendar.creator.type.recurrence.increaser.DateIncreaser;
import by.iba.bussiness.calendar.creator.type.recurrence.parser.IcalDateParser;
import by.iba.bussiness.calendar.date.constants.DateHelperConstants;
import by.iba.bussiness.calendar.date.model.reccurence.RecurrenceDateHelper;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.calendar.rrule.model.Rrule;
import by.iba.bussiness.calendar.session.model.Session;
import by.iba.bussiness.calendar.session.parser.SessionParser;
import by.iba.bussiness.meeting.timeslot.model.TimeSlot;
import by.iba.exception.CalendarException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.FixedUidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@Component
public class RecurrenceCalendarTemplateCreator {
    private Logger logger = LoggerFactory.getLogger(RecurrenceCalendarTemplateCreator.class);
    private CalendarTextEditor calendarTextEditor;
    private Calendar requestCalendar;
    private SessionParser sessionParser;
    private DateHelperConstants dateHelperConstants;
    private IcalDateParser icalDateParser;
    private DateIncreaser dateIncreaser;

    @Autowired
    public RecurrenceCalendarTemplateCreator(CalendarTextEditor calendarTextEditor,
                                             @Qualifier("requestCalendar") Calendar requestCalendar,
                                             SessionParser sessionParser,
                                             DateHelperConstants dateHelperConstants, IcalDateParser icalDateParser, DateIncreaser dateIncreaser) {
        this.calendarTextEditor = calendarTextEditor;
        this.requestCalendar = requestCalendar;
        this.sessionParser = sessionParser;
        this.dateHelperConstants = dateHelperConstants;
        this.icalDateParser = icalDateParser;
        this.dateIncreaser = dateIncreaser;
    }

    public Calendar createRecurrenceCalendarInvitationTemplate(RecurrenceDateHelper recurrenceDateHelper, Meeting meeting) {
        Rrule rrule = recurrenceDateHelper.getRrule();
        DateList exDates = new DateList();
        rrule.getExDates().forEach(x -> exDates.add(new Date(x)));
        List<TimeSlot> meetingTimeSlots = meeting.getTimeSlots();
        TimeSlot firstTimeSlot = meetingTimeSlots.get(dateHelperConstants.getNumberOfFirstTimeSlot());
        TimeSlot lastTimeSlot = meetingTimeSlots.get(meetingTimeSlots.size() - 1);
        Session lastSession = sessionParser.timeSlotToSession(lastTimeSlot);
        String increasedDate = dateIncreaser.increaseAndParse(rrule.getRruleFreqType(), rrule.getInterval(), lastSession.getStartDate());
        String until = icalDateParser.parseToIcalDate(increasedDate);
        Session firstSession = sessionParser.timeSlotToSession(firstTimeSlot);
        DateTime startDateTime = new DateTime(firstSession.getStartDate());
        DateTime endDateTime = new DateTime(firstSession.getEndDate());

        requestCalendar.getComponents().add(new VEvent(startDateTime, endDateTime, meeting.getSummary()));
        net.fortuna.ical4j.model.Component event = requestCalendar.getComponents().getComponent(net.fortuna.ical4j.model.Component.VEVENT);

        event.getProperties().add(new Sequence("0"));
        event.getProperties().add(new Location(calendarTextEditor.breakLine(meeting.getLocation())));
        event.getProperties().add(new Description(calendarTextEditor.breakLine(meeting.getDescription())));

        FixedUidGenerator fixedUidGenerator;
        try {
            Recur recurrence = new Recur("FREQ=" + rrule.getRruleFreqType().toString() + ";" + "INTERVAL=" + rrule.getInterval() + ";" + "UNTIL=" + until + ";");
            event.getProperties().add(new RRule(recurrence));
            event.getProperties().add(new ExDate(exDates));
            event.getProperties().add(new Organizer("mailto:" + meeting.getOwner().getEmail()));
            fixedUidGenerator = new FixedUidGenerator("YourLearning");
        } catch (ParseException | URISyntaxException | SocketException e) {
            logger.error(e.getMessage());
            throw new CalendarException("Can't create calendar meeting. Try again later");
        }
        Uid UID = fixedUidGenerator.generateUid();
        event.getProperties().add(UID);

        return requestCalendar;

    }
}
