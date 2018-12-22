package by.iba.bussiness.calendar.creator.type.complex;

import by.iba.bussiness.calendar.creator.text_preparing.CalendarTextEditor;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.calendar.date.model.complex.ComplexDateHelper;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ComplexCalendarTemplateCreator {
    private CalendarTextEditor calendarTextEditor;
    private Calendar requestCalendar;
    private MeetingService meetingService;

    @Autowired
    public ComplexCalendarTemplateCreator(CalendarTextEditor calendarTextEditor,
                                          @Qualifier("publishCalendar") Calendar requestCalendar,
                                          MeetingService meetingService) {
        this.calendarTextEditor = calendarTextEditor;
        this.requestCalendar = requestCalendar;
        this.meetingService = meetingService;
    }

    public Calendar createComplexCalendarInvitationTemplate(ComplexDateHelper complexDateHelper, Meeting meeting) {
        return requestCalendar;
    }
}
