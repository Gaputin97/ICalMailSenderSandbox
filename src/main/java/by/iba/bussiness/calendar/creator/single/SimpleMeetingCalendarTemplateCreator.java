package by.iba.bussiness.calendar.creator.single;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.creator.definer.SequenceDefiner;
import by.iba.bussiness.calendar.creator.definer.UidDefiner;
import by.iba.bussiness.calendar.date.model.single.SingleDateHelper;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.enrollment.Enrollment;
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
    private Calendar requestCalendar;
    private Calendar cancelCalendar;
    private UidDefiner uidDefiner;
    private SequenceDefiner sequenceDefiner;

    @Autowired
    public SimpleMeetingCalendarTemplateCreator(@Qualifier("requestCalendar") Calendar requestCalendar,
                                                @Qualifier("cancelCalendar") Calendar cancelCalendar,
                                                UidDefiner uidDefiner,
                                                SequenceDefiner sequenceDefiner) {
        this.requestCalendar = requestCalendar;
        this.cancelCalendar = cancelCalendar;
        this.uidDefiner = uidDefiner;
        this.sequenceDefiner = sequenceDefiner;
    }

    public Calendar createSimpleMeetingInvitationTemplate(SingleDateHelper singleDateHelper, Appointment appointment, Enrollment enrollment) {
        logger.info("Started creating invitation ics file with single meeting with id " + appointment.getMeetingId());
        Calendar calendar;
        VEvent event;
        try {
            Sequence sequence = sequenceDefiner.defineSequence(appointment, enrollment);
            Organizer organizer = new Organizer("mailto:" + appointment.getOwner().getEmail());
            Location location = new Location(appointment.getLocation());
            Description description = new Description((appointment.getDescription()));
            Summary summary = new Summary((appointment.getSummary()));
            Uid UID = uidDefiner.defineUid(enrollment);
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

    public Calendar createSimpleMeetingCancellationTemplate(SingleDateHelper singleDateHelper, Appointment appointment, Enrollment enrollment) {
        logger.info("Started creating cancellation ics file with single meeting with id " + appointment.getId());
        Calendar calendar;
        VEvent event;
        try {
            Sequence sequence = sequenceDefiner.defineSequence(appointment, enrollment);
            Organizer organizer = new Organizer("mailto:" + appointment.getOwner().getEmail());
            Location location = new Location((appointment.getLocation()));
            Description description = new Description((appointment.getDescription()));
            Summary summary = new Summary((appointment.getSummary()));
            Uid UID = uidDefiner.defineUid(enrollment);
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