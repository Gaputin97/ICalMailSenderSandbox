package by.iba.bussiness.calendar.creator.type.recurrence;

import by.iba.bussiness.calendar.creator.text_preparing.CalendarTextEditor;
<<<<<<< HEAD

=======
import by.iba.bussiness.calendar.creator.type.recurrence.increaser.DateIncreaser;
>>>>>>> dev1
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

<<<<<<< HEAD
import java.io.IOException;
=======
>>>>>>> dev1
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
<<<<<<< HEAD
    private IcalDateParser iСalDateParser;
=======
    private IcalDateParser icalDateParser;
    private DateIncreaser dateIncreaser;
>>>>>>> dev1

    @Autowired
    public RecurrenceCalendarTemplateCreator(CalendarTextEditor calendarTextEditor,
                                             @Qualifier("requestCalendar") Calendar requestCalendar,
                                             SessionParser sessionParser,
<<<<<<< HEAD
                                             DateHelperConstants dateHelperConstants, IcalDateParser iСalDateParser) {
=======
                                             DateHelperConstants dateHelperConstants, IcalDateParser icalDateParser, DateIncreaser dateIncreaser) {
>>>>>>> dev1
        this.calendarTextEditor = calendarTextEditor;
        this.requestCalendar = requestCalendar;
        this.sessionParser = sessionParser;
        this.dateHelperConstants = dateHelperConstants;
<<<<<<< HEAD
        this.iСalDateParser = iСalDateParser;
=======
        this.icalDateParser = icalDateParser;
        this.dateIncreaser = dateIncreaser;
>>>>>>> dev1
    }

    public Calendar createRecurrenceCalendarInvitationTemplate(RecurrenceDateHelper recurrenceDateHelper, Meeting meeting) {
        Rrule rrule = recurrenceDateHelper.getRrule();
        DateList exDates = new DateList();
        rrule.getExDates().forEach(x -> exDates.add(new Date(x)));

        List<TimeSlot> meetingTimeSlots = meeting.getTimeSlots();
        TimeSlot firstTimeSlot = meetingTimeSlots.get(dateHelperConstants.getNumberOfFirstTimeSlot());
        TimeSlot lastTimeSlot = meetingTimeSlots.get(meetingTimeSlots.size() - 1);
<<<<<<< HEAD
        String until = iСalDateParser.parseToICalDate(lastTimeSlot.getStartDateTime());
=======
        Session lastSession = sessionParser.timeSlotToSession(lastTimeSlot);
        String increasedDate = dateIncreaser.increaseAndParse(rrule.getRruleFreqType(), rrule.getInterval(), lastSession.getStartDate());
        String until = icalDateParser.parseToIcalDate(increasedDate);
>>>>>>> dev1
        Session firstSession = sessionParser.timeSlotToSession(firstTimeSlot);
        DateTime startDateTime = new DateTime(firstSession.getStartDate());
        DateTime endDateTime = new DateTime(firstSession.getEndDate());

<<<<<<< HEAD
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
        calendar.getComponents().add(new VEvent(startDateTime, meeting.getSummary()));
=======
        requestCalendar.getComponents().add(new VEvent(startDateTime, endDateTime, meeting.getSummary()));
>>>>>>> dev1
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

        return calendar;

    }
}
