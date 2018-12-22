package by.iba.bussiness.calendar.creator.type.recurrence;

import by.iba.bussiness.calendar.creator.text_preparing.CalendarTextEditor;
import by.iba.bussiness.calendar.creator.type.recurrence.parser.IcalDateParser;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.wrapper.constants.MeetingWrapperConstants;
import by.iba.bussiness.meeting.wrapper.model.reccurence.RecurrenceMeetingWrapper;
import by.iba.bussiness.rrule.model.Rrule;
import by.iba.bussiness.session.model.Session;
import by.iba.bussiness.session.parser.SessionParser;
import by.iba.bussiness.timeslot.model.TimeSlot;
import by.iba.exception.ServiceException;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.FixedUidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@Component
public class RecurrenceCalendarTemplate {
    private CalendarTextEditor calendarTextEditor;
    private Calendar requestCalendar;
    private SessionParser sessionParser;
    private MeetingWrapperConstants meetingWrapperConstants;
    private IcalDateParser icalDateParser;

    @Autowired
    public RecurrenceCalendarTemplate(CalendarTextEditor calendarTextEditor,
                                      @Qualifier("requestCalendar") Calendar requestCalendar,
                                      SessionParser sessionParser,
                                      MeetingWrapperConstants meetingWrapperConstants, IcalDateParser icalDateParser) {
        this.calendarTextEditor = calendarTextEditor;
        this.requestCalendar = requestCalendar;
        this.sessionParser = sessionParser;
        this.meetingWrapperConstants = meetingWrapperConstants;
        this.icalDateParser = icalDateParser;
    }

    public Calendar createRecurrenceCalendarInvitationTemplate(RecurrenceMeetingWrapper recurrenceMeetingWrapper,
                                                               HttpServletRequest request,
                                                               Meeting meeting) {
        Rrule rrule = recurrenceMeetingWrapper.getRrule();
        DateList exDates = new DateList();
        rrule.getExDates().forEach(x -> exDates.add(new Date(x)));
        List<TimeSlot> meetingTimeSlots = meeting.getTimeSlots();
        TimeSlot firstTimeSlot = meetingTimeSlots.get(meetingWrapperConstants.getNumberOfFirstTimeSlot());
        TimeSlot lastTimeSlot = meetingTimeSlots.get(meetingTimeSlots.size() - 1);
        String until = icalDateParser.parseToIcalDate(lastTimeSlot.getStartDateTime());
        Session firstSession = sessionParser.timeSlotToSession(firstTimeSlot);
        DateTime startDateTime = new DateTime(firstSession.getStartDate());

        requestCalendar.getComponents().add(new VEvent(startDateTime, meeting.getSummary()));
        net.fortuna.ical4j.model.Component event = requestCalendar.getComponents().getComponent(net.fortuna.ical4j.model.Component.VEVENT);

        event.getProperties().add(new Sequence("0"));
        event.getProperties().add(new Location(calendarTextEditor.lineBreak(meeting.getLocation())));
        event.getProperties().add(new Description(calendarTextEditor.lineBreak(meeting.getDescription())));

        FixedUidGenerator fixedUidGenerator = null;
        try {
            Recur recurrence = new Recur("FREQ=" + rrule.getRruleFreqType().toString() + ";" + "INTERVAL=" + rrule.getInterval() + ";" + "UNTIL=" + until + ";");
            event.getProperties().add(new RRule(recurrence));
            event.getProperties().add(new ExDate(exDates));
            event.getProperties().add(new Organizer("mailto:" + meeting.getOwner().getEmail()));
            fixedUidGenerator = new FixedUidGenerator("YourLearning");
        } catch (ParseException | URISyntaxException | SocketException e) {
            throw new ServiceException(e.getMessage());
        }
        Uid UID = fixedUidGenerator.generateUid();
        event.getProperties().add(UID);

        return requestCalendar;

    }
}
