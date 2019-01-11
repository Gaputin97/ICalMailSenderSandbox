package by.iba.bussiness.calendar.creator.simple;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.calendar.creator.definer.SequenceDefiner;
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
public class SingleMeetingCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(SingleMeetingCalendarTemplateCreator.class);
    private Calendar requestCalendar;
    private Calendar cancelCalendar;
    private SequenceDefiner sequenceDefiner;
    private CalendarTextEditor calendarTextEditor;

    @Autowired
    public SingleMeetingCalendarTemplateCreator(@Qualifier("requestCalendar") Calendar requestCalendar,
                                                @Qualifier("cancelCalendar") Calendar cancelCalendar,
                                                SequenceDefiner sequenceDefiner,
                                                CalendarTextEditor calendarTextEditor) {
        this.requestCalendar = requestCalendar;
        this.cancelCalendar = cancelCalendar;
        this.sequenceDefiner = sequenceDefiner;
        this.calendarTextEditor = calendarTextEditor;
    }

    public Calendar createSingleMeetingInvitationTemplate(SingleDateHelper singleDateHelper, Appointment appointment, Enrollment enrollment) {
        logger.debug("Started creating invitation ics file with simple meeting with id " + appointment.getMeetingId());
        return createCommonSimpleTemplate(singleDateHelper, appointment, enrollment, requestCalendar);
    }

    public Calendar createSingleMeetingCancellationTemplate(SingleDateHelper singleDateHelper, Appointment appointment, Enrollment enrollment) {
        logger.debug("Started creating cancellation ics file with simple meeting with id " + appointment.getId());
        return createCommonSimpleTemplate(singleDateHelper, appointment, enrollment, cancelCalendar);
    }

    private Calendar createCommonSimpleTemplate(SingleDateHelper singleDateHelper, Appointment appointment, Enrollment enrollment, Calendar concreteCalendar) {
        logger.debug("Started creating cancellation ics file with simple meeting with id " + appointment.getId());
        Calendar calendar;
        VEvent event;
        try {
            Sequence sequence = sequenceDefiner.defineSequence(appointment);
            Organizer organizer = new Organizer("mailto:" + appointment.getOwner().getEmail());
            Location location = new Location((appointment.getLocation()));
            Description description = new Description((appointment.getDescription()));
            Summary summary = new Summary((appointment.getSummary()));
            summary.setValue(calendarTextEditor.deleteSummaryWord(summary.getValue()));
            Uid UID = new Uid(enrollment.getCurrentCalendarUid());

            Session session = singleDateHelper.getSession();
            DateTime startDateTime = new DateTime(session.getStartDate());
            DateTime endDateTime = new DateTime(session.getEndDate());

            calendar = new Calendar(concreteCalendar);
            event = new VEvent(startDateTime, endDateTime, summary.toString());
            event.getProperties().addAll(Arrays.asList(sequence, organizer, location, description, summary, UID));
            calendar.getComponents().add(event);
        } catch (ParseException | URISyntaxException | IOException e) {
            logger.error(e.getMessage());
            throw new CalendarException("Can't create simple calendar meeting. Try again later");
        }
        return calendar;
    }
}