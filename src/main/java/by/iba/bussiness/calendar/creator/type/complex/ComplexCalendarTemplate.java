package by.iba.bussiness.calendar.creator.type.complex;

import by.iba.bussiness.calendar.creator.text_preparing.CalendarTextEditor;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.meeting.wrapper.model.complex.ComplexMeetingWrapper;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ComplexCalendarTemplate {
    private CalendarTextEditor calendarTextEditor;
    private Calendar requestCalendar;
    private MeetingService meetingService;

    @Autowired
    public ComplexCalendarTemplate(CalendarTextEditor calendarTextEditor,
                                   @Qualifier("publishCalendar") Calendar requestCalendar,
                                   MeetingService meetingService) {
        this.calendarTextEditor = calendarTextEditor;
        this.requestCalendar = requestCalendar;
        this.meetingService = meetingService;
    }

    public Calendar createComplexCalendarInvitationTemplate(ComplexMeetingWrapper complexMeetingWrapper, HttpServletRequest request) {
        return requestCalendar;
    }
}
