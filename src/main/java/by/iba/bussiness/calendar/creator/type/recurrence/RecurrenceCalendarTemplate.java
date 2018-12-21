package by.iba.bussiness.calendar.creator.type.recurrence;

import by.iba.bussiness.calendar.creator.text_preparing.CalendarTextFieldBreaker;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.meeting.wrapper.model.reccurence.RecurrenceMeetingWrapper;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RecurrenceCalendarTemplate {
    private CalendarTextFieldBreaker calendarTextFieldBreaker;
    private Calendar requestCalendar;
    private MeetingService meetingService;

    @Autowired
    public RecurrenceCalendarTemplate(CalendarTextFieldBreaker calendarTextFieldBreaker,
                                      @Qualifier("requestCalendar") Calendar requestCalendar,
                                      MeetingService meetingService) {
        this.calendarTextFieldBreaker = calendarTextFieldBreaker;
        this.requestCalendar = requestCalendar;
        this.meetingService = meetingService;
    }

    public Calendar createRecurrenceCalendarInvitationTemplate(RecurrenceMeetingWrapper recurrenceMeetingWrapper, HttpServletRequest request, Meeting meeting) {
//        Session timeSlot = recurrenceMeetingWrapper.getSession();
//        DateTime startDateTime = new DateTime(timeSlot.getStartDate());
//        DateTime endDateTime = new DateTime(timeSlot.getEndDate());
//
//        requestCalendar.getComponents().add(new VEvent(startDateTime, endDateTime,));
//        net.fortuna.ical4j.model.Component event = requestCalendar.getComponents().getComponent(net.fortuna.ical4j.model.Component.VEVENT);
//
//        event.getProperties().add(new Sequence("0")); //HAAARDCODE, remake after adding appointment
//        event.getProperties().add(new Location(calendarTextFieldBreaker.lineBreak(meeting.getLocation())));
//        event.getProperties().add(new Description(calendarTextFieldBreaker.lineBreak(meeting.getDescription())));
//
//        FixedUidGenerator fixedUidGenerator = null;
//        try {
//            event.getProperties().add(new Organizer("mailto:" + meeting.getOwner().getEmail()));
//            fixedUidGenerator = new FixedUidGenerator("YourLearning");
//        } catch (URISyntaxException | SocketException e) {
//            e.printStackTrace();
//        }
//
//        // Remake after enter appointment
//        Uid UID = fixedUidGenerator.generateUid();
//        event.getProperties().add(UID);
//        //requestCalendar.getComponents().add(event);

        return requestCalendar;
    }
}
