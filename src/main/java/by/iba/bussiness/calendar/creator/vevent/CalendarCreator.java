package by.iba.bussiness.calendar.creator.vevent;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.CalendarRruleParser;
import by.iba.bussiness.calendar.creator.definer.SequenceDefiner;
import by.iba.bussiness.calendar.creator.simple.DateIncreaser;
import by.iba.bussiness.calendar.creator.simple.DateParser;
import by.iba.bussiness.calendar.creator.simple.ICalDateParser;
import by.iba.bussiness.calendar.rrule.RruleCount;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.exception.CalendarException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.AltRep;
import net.fortuna.ical4j.model.parameter.FmtType;
import net.fortuna.ical4j.model.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;

@Component
public class CalendarCreator {
    private static final Logger logger = LoggerFactory.getLogger(CalendarCreator.class);
    private static final String RICH_TEXT_CID = "rich_description";
    private static final String BODY_OPEN_TAG = "<body>";
    private static final String BODY_CLOSE_TAG = "</body>";
    private ICalDateParser iСalDateParser;
    private DateIncreaser dateIncreaser;
    private SequenceDefiner sequenceDefiner;
    private CalendarRruleParser calendarRruleParser;
    private DateParser dateParser;
    private Calendar requestCalendar;
    private Calendar cancelCalendar;

    @Autowired
    public CalendarCreator(ICalDateParser icalDateParser,
                           DateIncreaser dateIncreaser,
                           SequenceDefiner sequenceDefiner,
                           CalendarRruleParser calendarRruleParser,
                           DateParser dateParser,
                           @Qualifier("requestCalendar") Calendar requestCalendar,
                           @Qualifier("cancelCalendar") Calendar cancelCalendar) {
        this.iСalDateParser = icalDateParser;
        this.dateIncreaser = dateIncreaser;
        this.sequenceDefiner = sequenceDefiner;
        this.calendarRruleParser = calendarRruleParser;
        this.dateParser = dateParser;
        this.requestCalendar = requestCalendar;
        this.cancelCalendar = cancelCalendar;
    }

    public Calendar createCalendarTemplate(Rrule rrule, Appointment newAppointment) {
        DateList exDatesList = new DateList();
        rrule.getExDates().forEach(exDate -> exDatesList.add(new DateTime(exDate.toEpochMilli())));

        Instant startAppDate = dateParser.parseDate(newAppointment.getStartDateTime());
        Instant endAppdate = dateParser.parseDate(newAppointment.getEndDateTime());

        long interval = rrule.getInterval();
        Frequency frequency = rrule.getFrequency();
        String increasedUntilDate = dateIncreaser.increaseDate(frequency, interval, endAppdate);
        String richDescription = BODY_OPEN_TAG + newAppointment.getDescription() + BODY_CLOSE_TAG;
        String parsedIncreasedUntilDate = iСalDateParser.parseToICalDate(increasedUntilDate);
        Recur recurrence = calendarRruleParser.parseToCalendarRrule(rrule, parsedIncreasedUntilDate);
        Calendar calendar;
        try {
            Sequence sequence = sequenceDefiner.defineSequence(newAppointment);
            Organizer organizer = new Organizer("mailto:" + newAppointment.getFrom());
            Location location = new Location((newAppointment.getLocation()));
            if (!rrule.getRruleCount().equals(RruleCount.ZERO)) {
                exDatesList.add(new DateTime(parsedIncreasedUntilDate));
            }
            RRule rRule = new RRule(recurrence);
            Uid UID = new Uid(newAppointment.getId().toString());
            DateTime startDateTime = new DateTime(startAppDate.toEpochMilli());
            DateTime endDateTime = new DateTime(endAppdate.toEpochMilli());
            Description description = new Description(newAppointment.getPlainDescription());
            AltRep altRep = new AltRep("CID:" + RICH_TEXT_CID);
            description.getParameters().add(altRep);

            XProperty xAltDesc = new XProperty("X-ALT-DESC");
            xAltDesc.getParameters().add(new FmtType("text/html"));
            xAltDesc.setValue(richDescription);
            calendar = new Calendar(requestCalendar);
            VEvent event = new VEvent(startDateTime, endDateTime, newAppointment.getSummary());
            calendar.getComponents().add(event);
            if (!exDatesList.isEmpty()) {
                ExDate exDates = new ExDate(exDatesList);
                event.getProperties().add(exDates);
            }
            event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, UID, rRule, xAltDesc));
        } catch (ParseException | IOException | URISyntaxException e) {
            logger.error("Cant create recur calendar meeting" + e);
            throw new CalendarException("Can't create simple calendar meeting. Try again later");
        }
        return calendar;
    }

    public Calendar createCalendarCancellationTemplate(Appointment appointment) {

        Instant startAppDate = dateParser.parseDate(appointment.getStartDateTime());
        Instant endAppDate = dateParser.parseDate(appointment.getEndDateTime());

        String richDescription = BODY_OPEN_TAG + appointment.getDescription() + BODY_CLOSE_TAG;
        Calendar calendar;
        try {
            Sequence sequence = sequenceDefiner.defineSequence(appointment);
            Organizer organizer = new Organizer("mailto:" + appointment.getFrom());
            Location location = new Location((appointment.getLocation()));
            Uid UID = new Uid(appointment.getId().toString());
            DateTime startDateTime = new DateTime(startAppDate.toEpochMilli());
            DateTime endDateTime = new DateTime(endAppDate.toEpochMilli());
            Description description = new Description(appointment.getPlainDescription());
            AltRep altRep = new AltRep("CID:" + RICH_TEXT_CID);
            description.getParameters().add(altRep);

            XProperty xAltDesc = new XProperty("X-ALT-DESC");
            xAltDesc.getParameters().add(new FmtType("text/html"));
            xAltDesc.setValue(richDescription);
            calendar = new Calendar(cancelCalendar);
            VEvent event = new VEvent(startDateTime, endDateTime, appointment.getSummary());
            calendar.getComponents().add(event);
            event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, UID, xAltDesc));
        } catch (ParseException | IOException | URISyntaxException e) {
            logger.error("Cant create recur calendar meeting" + e);
            throw new CalendarException("Can't create simple calendar meeting. Try again later");
        }
        return calendar;
    }
}
