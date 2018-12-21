package by.iba.bussiness.calendar.creator.type.single;

import by.iba.bussiness.calendar.creator.text_preparing.CalendarTextFieldBreaker;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.meeting.wrapper.model.single.SingleMeetingWrapper;
import by.iba.bussiness.session.model.Session;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.CalendarComponent;
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
public class SimpleCalendarTemplate {
    private CalendarTextFieldBreaker calendarTextFieldBreaker;
    private Calendar requestCalendar;
    private MeetingService meetingService;

    @Autowired
    public SimpleCalendarTemplate(CalendarTextFieldBreaker calendarTextFieldBreaker,
                                  @Qualifier("requestCalendar") Calendar requestCalendar,
                                  MeetingService meetingService) {
        this.calendarTextFieldBreaker = calendarTextFieldBreaker;
        this.requestCalendar = requestCalendar;
        this.meetingService = meetingService;
    }

    public Calendar createSingleMeetingInvitationTemplate(SingleMeetingWrapper singleMeetingWrapper, Meeting meeting) {
        Session timeSlot = singleMeetingWrapper.getSession();
        DateTime startDateTime = new DateTime(timeSlot.getStartDate());
        DateTime endDateTime = new DateTime(timeSlot.getEndDate());
        String summary = calendarTextFieldBreaker.lineBreak(meeting.getSummary());

        requestCalendar.getComponents().add(new VEvent(startDateTime, endDateTime, summary));
        CalendarComponent event = requestCalendar.getComponents().getComponent(CalendarComponent.VEVENT);

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

        return requestCalendar;
    }
}
