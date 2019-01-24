package by.iba.bussiness.calendar.creator;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.CalendarRruleParser;
import by.iba.bussiness.calendar.creator.definer.SequenceDefiner;
import by.iba.bussiness.calendar.creator.simple.DateIncreaser;
import by.iba.bussiness.calendar.creator.simple.IcalDateParser;
import by.iba.bussiness.calendar.rrule.Count;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.session.Session;
import by.iba.exception.CalendarException;
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
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class VEventCreator {
    private static final Logger logger = LoggerFactory.getLogger(VEventCreator.class);
    private static final String RICH_TEXT_CID = "rich_description";
    private static final String BODY_OPEN_TAG = "<body>";
    private static final String BODY_CLOSE_TAG = "</body>";
    private static final int NUMBER_OF_FIRST_TIME_SLOT = 0;
    private IcalDateParser iСalDateParser;
    private DateIncreaser dateIncreaser;
    private SequenceDefiner sequenceDefiner;
    private CalendarRruleParser calendarRruleParser;

    @Autowired
    public VEventCreator(IcalDateParser icalDateParser,
                         DateIncreaser dateIncreaser,
                         SequenceDefiner sequenceDefiner,
                         CalendarRruleParser calendarRruleParser) {
        this.iСalDateParser = icalDateParser;
        this.dateIncreaser = dateIncreaser;
        this.sequenceDefiner = sequenceDefiner;
        this.calendarRruleParser = calendarRruleParser;
    }

    public VEvent createCommonVEventTemplate(Rrule rrule, Appointment appointment) {
        DateList exDatesList = new DateList();
        rrule.getExDates().forEach(exDate -> exDatesList.add(new DateTime(exDate.toEpochMilli())));
        List<Session> sessions = new ArrayList<>(appointment.getSessionList());
        Collections.sort(sessions);

        Session firstSession = sessions.get(NUMBER_OF_FIRST_TIME_SLOT);
        Session lastSession = sessions.get(sessions.size() - 1);
        Instant startDateOfLastSession = lastSession.getStartDateTime();
        Instant startDateOfFirstSession = firstSession.getStartDateTime();
        Instant endDateOfFirstSession = firstSession.getEndDateTime();

        long interval = rrule.getInterval();
        Frequency frequency = rrule.getFrequency();
        String increasedUntilDate = dateIncreaser.increaseDate(frequency, interval, startDateOfLastSession);
        String richDescription = BODY_OPEN_TAG + appointment.getDescription() + BODY_CLOSE_TAG;
        String parsedIncreasedUntilDate = iСalDateParser.parseToICalDate(increasedUntilDate);
        Recur recurrence = calendarRruleParser.parseToCalendarRrule(rrule, parsedIncreasedUntilDate);
        VEvent event;
        try {
            Sequence sequence = sequenceDefiner.defineSequence(appointment);
            Organizer organizer = new Organizer("mailto:" + appointment.getFrom());
            Location location = new Location((appointment.getLocation()));
            if (!rrule.getCount().equals(Count.DEFAULT)) {
                exDatesList.add(new DateTime(parsedIncreasedUntilDate));
            }
            RRule rRule = new RRule(recurrence);
            Uid UID = new Uid(appointment.getId().toString());
            DateTime startDateTime = new DateTime(startDateOfFirstSession.toEpochMilli());
            DateTime endDateTime = new DateTime(endDateOfFirstSession.toEpochMilli());

            Description description = new Description("Plain description");
            AltRep altRep = new AltRep("CID:" + RICH_TEXT_CID);
            description.getParameters().add(altRep);

            XProperty xAltDesc = new XProperty("X-ALT-DESC");
            xAltDesc.getParameters().add(new FmtType("text/html"));
            xAltDesc.setValue(richDescription);

            event = new VEvent(startDateTime, endDateTime, appointment.getSummary());
            if (!exDatesList.isEmpty()) {
                ExDate exDates = new ExDate(exDatesList);
                event.getProperties().add(exDates);
            }
            event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, UID, rRule, xAltDesc));
        } catch (ParseException | URISyntaxException e) {
            logger.error("Cant create recur calendar meeting" + e.getMessage());
            throw new CalendarException("Can't create simple calendar meeting. Try again later");
        }
        return event;
    }
}
