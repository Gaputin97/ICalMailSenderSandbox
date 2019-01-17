package by.iba.bussiness.calendar.creator.recurrence;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.calendar.creator.definer.SequenceDefiner;
import by.iba.bussiness.calendar.date.helper.model.reccurence.RecurrenceDateHelper;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.exception.CalendarException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Recur;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Component
public class RecurrenceMeetingCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(RecurrenceMeetingCalendarTemplateCreator.class);
    private static final int NUMBER_OF_FIRST_TIME_SLOT = 0;
    private Calendar requestCalendar;
    private IcalDateParser iСalDateParser;
    private DateIncreaser dateIncreaser;
    private Calendar cancelCalendar;
    private SequenceDefiner sequenceDefiner;

    @Autowired
    public RecurrenceMeetingCalendarTemplateCreator(@Qualifier("requestCalendar") Calendar requestCalendar,
                                                    IcalDateParser icalDateParser,
                                                    DateIncreaser dateIncreaser,
                                                    @Qualifier("cancelCalendar") Calendar cancelCalendar,
                                                    SequenceDefiner sequenceDefiner)
    {
        this.requestCalendar = requestCalendar;
        this.iСalDateParser = icalDateParser;
        this.dateIncreaser = dateIncreaser;
        this.cancelCalendar = cancelCalendar;
        this.sequenceDefiner = sequenceDefiner;
    }

    public Calendar createRecurrenceCalendarInvitationTemplate(RecurrenceDateHelper recurrenceDateHelper, Appointment appointment, Enrollment enrollment) {
        logger.debug("Started creating inv/update ics file with recurrence meeting with id " + appointment.getId());
        return createCommonRecurrenceTemplate(recurrenceDateHelper, appointment, enrollment, requestCalendar);
    }

    public Calendar createRecurrenceCalendarCancellationTemplate(RecurrenceDateHelper recurrenceDateHelper, Appointment appointment, Enrollment enrollment) {
        logger.debug("Started creating cancellation ics file with recurrence meeting with id " + appointment.getId());
        return createCommonRecurrenceTemplate(recurrenceDateHelper, appointment, enrollment, cancelCalendar);

    }

    private Calendar createCommonRecurrenceTemplate(RecurrenceDateHelper recurrenceDateHelper,
                                                   Appointment appointment,
                                                   Enrollment enrollment,
                                                   Calendar concreteCalendar) {
        Rrule rrule = recurrenceDateHelper.getRrule();
        DateList exDatesList = new DateList();
        rrule.getExDates().forEach(x -> exDatesList.add(new DateTime(x)));
        List<Session> sessions = appointment.getSessionList();
        Collections.sort(sessions);

        Session firstSession = sessions.get(NUMBER_OF_FIRST_TIME_SLOT);
        Session lastSession = sessions.get(sessions.size() - 1);
        Date startDateOfLastSession = lastSession.getStartDateTime();
        Date startDateOfFirstSession = firstSession.getStartDateTime();
        Date endDateOfFirstSession = firstSession.getEndDateTime();

        long interval = rrule.getInterval();
        Frequency frequency = rrule.getFrequency();
        String increasedUntilDate = dateIncreaser.increaseAndParse(frequency, interval, startDateOfLastSession);
        Calendar calendar;
        try {
            Sequence sequence = sequenceDefiner.defineSequence(appointment);
            Organizer organizer = new Organizer("mailto:" + appointment.getOwner().getEmail());
            Location location = new Location((appointment.getLocation()));
            Description description = new Description((appointment.getDescription()));
            String increasedUntilString = iСalDateParser.parseToICalDate(increasedUntilDate);
            exDatesList.add(new DateTime(increasedUntilString));

            Recur recurrence = new Recur("FREQ=" + frequency.toString() + ";" + "INTERVAL="
                    + interval + ";" + "UNTIL=" + increasedUntilString + ";");
            RRule rRule = new RRule(recurrence);
            Uid UID = new Uid(enrollment.getCurrentCalendarUid());
            DateTime startDateTime = new DateTime(startDateOfFirstSession);
            DateTime endDateTime = new DateTime(endDateOfFirstSession);

            calendar = new Calendar(concreteCalendar);
            VEvent event = new VEvent(startDateTime, endDateTime, appointment.getSummary());
            if (!exDatesList.isEmpty()) {
                ExDate exDates = new ExDate(exDatesList);
                event.getProperties().add(exDates);
            }
            event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, UID, rRule));
            calendar.getComponents().add(event);
        } catch (ParseException | URISyntaxException | IOException e) {
            logger.error("Cant create recur calendar meeting" + e.getMessage());
            throw new CalendarException("Can't create recurrence calendar meeting. Try again later");
        }

        return calendar;
    }
}