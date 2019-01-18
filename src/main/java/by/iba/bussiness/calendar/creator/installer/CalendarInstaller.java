package by.iba.bussiness.calendar.creator.installer;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.creator.definer.SequenceDefiner;
import by.iba.bussiness.calendar.creator.simple.DateIncreaser;
import by.iba.bussiness.calendar.creator.simple.IcalDateParser;
import by.iba.bussiness.calendar.creator.simple.SimpleMetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.session.Session;
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
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class CalendarInstaller {
    private static final Logger logger = LoggerFactory.getLogger(CalendarInstaller.class);
    private static final String RICH_TEXT_CID = "calendar_rich_text_description";
    private static final int NUMBER_OF_FIRST_TIME_SLOT = 0;
    private IcalDateParser iСalDateParser;
    private DateIncreaser dateIncreaser;
    private SequenceDefiner sequenceDefiner;

    @Autowired
    public CalendarInstaller(IcalDateParser icalDateParser,
                             DateIncreaser dateIncreaser,
                             SequenceDefiner sequenceDefiner) {
        this.iСalDateParser = icalDateParser;
        this.dateIncreaser = dateIncreaser;
        this.sequenceDefiner = sequenceDefiner;
    }

    public Calendar installCalendarCommonParts(Rrule rrule, Appointment appointment) {
        DateList exDatesList = new DateList();
        rrule.getExDates().forEach(x -> exDatesList.add(new DateTime(x.toEpochMilli())));
        List<Session> sessions = appointment.getSessionList();
        Collections.sort(sessions);

        Session firstSession = sessions.get(NUMBER_OF_FIRST_TIME_SLOT);
        Session lastSession = sessions.get(sessions.size() - 1);
        Instant startDateOfLastSession = lastSession.getStartDateTime();
        Instant startDateOfFirstSession = firstSession.getStartDateTime();
        Instant endDateOfFirstSession = firstSession.getEndDateTime();

        long interval = rrule.getInterval();
        Frequency frequency = rrule.getFrequency();
        String increasedUntilDate = dateIncreaser.increaseAndParse(frequency, interval, startDateOfLastSession);
        String appDescription = appointment.getDescription();
        Calendar calendar = new Calendar();
        try {
            Sequence sequence = sequenceDefiner.defineSequence(appointment);
            Organizer organizer = new Organizer("mailto:" + appointment.getOwner().getEmail());
            Location location = new Location((appointment.getLocation()));
            String increasedUntilString = iСalDateParser.parseToICalDate(increasedUntilDate);
            exDatesList.add(new DateTime(increasedUntilString));

            Recur recurrence = new Recur("FREQ=" + frequency.toString() + ";" + "INTERVAL="
                    + interval + ";" + "UNTIL=" + increasedUntilString + ";");
            RRule rRule = new RRule(recurrence);
            Uid UID = new Uid(appointment.getId().toString());
            DateTime startDateTime = new DateTime(startDateOfFirstSession.getNano());
            DateTime endDateTime = new DateTime(endDateOfFirstSession.getNano());

            Description description = new Description("HAARDCOOOOODE!!!!!");
            AltRep altRep = new AltRep("CID:" + RICH_TEXT_CID);
//            description.getParameters().add(altRep);

            XProperty xAltDesc = new XProperty("X-ALT-DESC");
            xAltDesc.getParameters().add(new FmtType("text/html"));
            xAltDesc.setValue(appDescription);

            VEvent event = new VEvent(startDateTime, endDateTime, appointment.getSummary());
            if (!exDatesList.isEmpty()) {
                ExDate exDates = new ExDate(exDatesList);
                event.getProperties().add(exDates);
            }
            event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, UID, rRule/*, xAltDesc*/));
            calendar.getComponents().add(event);
        } catch (ParseException | URISyntaxException e) {
            logger.error("Cant create recur calendar meeting" + e.getMessage());
            throw new CalendarException("Can't create simple calendar meeting. Try again later");
        }
        return calendar;
    }
}
