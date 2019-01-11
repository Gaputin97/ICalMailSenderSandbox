package by.iba.bussiness.calendar.creator.recurrence;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.creator.definer.SequenceDefiner;
import by.iba.bussiness.calendar.date.model.reccurence.RecurrenceDateHelper;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.exception.CalendarException;
import net.fortuna.ical4j.model.*;
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
import java.util.List;

@org.springframework.stereotype.Component
public class RecurrenceMeetingCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(RecurrenceMeetingCalendarTemplateCreator.class);
    private static final int NUMBER_OF_FIRST_TIME_SLOT = 0;
    private Calendar requestCalendar;
    private SessionParser sessionParser;
    private IcalDateParser iСalDateParser;
    private DateIncreaser dateIncreaser;
    private Calendar cancelCalendar;
    private SequenceDefiner sequenceDefiner;

    @Autowired
    public RecurrenceMeetingCalendarTemplateCreator(@Qualifier("requestCalendar") Calendar requestCalendar,
                                                    SessionParser sessionParser,
                                                    IcalDateParser icalDateParser,
                                                    DateIncreaser dateIncreaser,
                                                    @Qualifier("cancelCalendar") Calendar cancelCalendar,
                                                    SequenceDefiner sequenceDefiner) {
        this.requestCalendar = requestCalendar;
        this.sessionParser = sessionParser;
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
        rrule.getExDates().forEach(x -> exDatesList.add(new Date(x)));

        List<TimeSlot> meetingTimeSlots = appointment.getTimeSlots();
        TimeSlot firstTimeSlot = meetingTimeSlots.get(NUMBER_OF_FIRST_TIME_SLOT);
        TimeSlot lastTimeSlot = meetingTimeSlots.get(meetingTimeSlots.size() - 1);

        Session firstSession = sessionParser.timeSlotToSession(firstTimeSlot);
        Session lastSession = sessionParser.timeSlotToSession(lastTimeSlot);

        String increasedDate = dateIncreaser.increaseAndParse(rrule.getFrequency(), rrule.getInterval(), lastSession.getStartDate());
        Calendar calendar;
        VEvent event;
        try {
            Sequence sequence = sequenceDefiner.defineSequence(appointment);
            Organizer organizer = new Organizer("mailto:" + appointment.getOwner().getEmail());
            Location location = new Location((appointment.getLocation()));
            Description description = new Description((appointment.getDescription()));
            Summary summary = new Summary(appointment.getSummary());
            String frequency = rrule.getFrequency().toString();
            Long interval = rrule.getInterval();
            String until = iСalDateParser.parseToICalDate(increasedDate);
            ExDate exDates = new ExDate(exDatesList);

            Recur recurrence = new Recur("FREQ=" + frequency + ";" + "INTERVAL=" + interval + ";" + "UNTIL=" + until + ";");
            RRule rRule = new RRule(recurrence);
            Uid UID = new Uid(enrollment.getCurrentCalendarUid());
            DateTime startDateTime = new DateTime(firstSession.getStartDate());
            DateTime endDateTime = new DateTime(firstSession.getEndDate());

            calendar = new Calendar(concreteCalendar);
            event = new VEvent(startDateTime, endDateTime, summary.toString());
            event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, UID, rRule, exDates));
            calendar.getComponents().add(event);
        } catch (ParseException | URISyntaxException | IOException e) {
            logger.error(e.getMessage());
            throw new CalendarException("Can't create recurrence calendar meeting. Try again later");
        }

        return calendar;
    }
}