package by.iba.bussines.calendar.creator.type.recurrence;

import by.iba.bussines.calendar.creator.text_preparing.CalendarTextFieldBreaker;
import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.service.MeetingService;
import by.iba.bussines.meeting.wrapper.model.reccurence.RecurrenceMeetingWrapper;
import by.iba.bussines.session.model.Session;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.FixedUidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketException;
import java.net.URISyntaxException;

@Component
public class RecurrenceCalendarInvitationTemplate {
    private CalendarTextFieldBreaker calendarTextFieldBreaker;
    private Calendar requestCalendar;
    private MeetingService meetingService;

    @Autowired
    public RecurrenceCalendarInvitationTemplate(CalendarTextFieldBreaker calendarTextFieldBreaker,
                                            @Qualifier("requestCalendar") Calendar requestCalendar,
                                            MeetingService meetingService) {
        this.calendarTextFieldBreaker = calendarTextFieldBreaker;
        this.requestCalendar = requestCalendar;
        this.meetingService = meetingService;
    }

    public Calendar createRecurrenceCalendarInvitationTemplate(RecurrenceMeetingWrapper recurrenceMeetingWrapper, HttpServletRequest request) {
        Session timeSlot = recurrenceMeetingWrapper.getSession();
        DateTime startDateTime = new DateTime(timeSlot.getStartDate());
        DateTime endDateTime = new DateTime(timeSlot.getEndDate());
        Meeting meeting = meetingService.getMeetingById(request, recurrenceMeetingWrapper.getMeetingId());

        requestCalendar.getComponents().add(new VEvent(startDateTime, endDateTime,));
        net.fortuna.ical4j.model.Component event = requestCalendar.getComponents().getComponent(net.fortuna.ical4j.model.Component.VEVENT);

        event.getProperties().add(new Sequence("0")); //HAAARDCODE, remake after adding appointment
        event.getProperties().add(new Location(calendarTextFieldBreaker.lineBreak(meeting.getLocation())));
        event.getProperties().add(new Description(calendarTextFieldBreaker.lineBreak(meeting.getDescription())));

        FixedUidGenerator fixedUidGenerator = null;
        try {
            event.getProperties().add(new Organizer("mailto:" + meeting.getOwner().getEmail()));
            fixedUidGenerator = new FixedUidGenerator("YourLearning");
        } catch (URISyntaxException | SocketException e) {
            e.printStackTrace();
        }

        // Remake after enter appointment
        Uid UID = fixedUidGenerator.generateUid();
        event.getProperties().add(UID);
        //requestCalendar.getComponents().add(event);

        return requestCalendar;
    }
}
