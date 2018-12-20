package by.iba.bussines.calendar.creator.type.complex;

import by.iba.bussines.calendar.creator.text_preparing.CalendarTextFieldBreaker;
import by.iba.bussines.meeting.service.MeetingService;
import by.iba.bussines.meeting.wrapper.model.complex.ComplexMeetingWrapper;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ComplexCalendarTemplate {
    private CalendarTextFieldBreaker calendarTextFieldBreaker;
    private Calendar requestCalendar;
    private MeetingService meetingService;

    @Autowired
    public ComplexCalendarTemplate(CalendarTextFieldBreaker calendarTextFieldBreaker,
                                   @Qualifier("publishCalendar") Calendar requestCalendar,
                                   MeetingService meetingService) {
        this.calendarTextFieldBreaker = calendarTextFieldBreaker;
        this.requestCalendar = requestCalendar;
        this.meetingService = meetingService;
    }

    public Calendar createComplexCalendarInvitationTemplate(ComplexMeetingWrapper complexMeetingWrapper, HttpServletRequest request) {
        return requestCalendar;
    }
}
