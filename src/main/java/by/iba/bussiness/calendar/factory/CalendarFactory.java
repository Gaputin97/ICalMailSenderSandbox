package by.iba.bussiness.calendar.factory;

import by.iba.bussiness.calendar.creator.type.complex.ComplexCalendarTemplate;
import by.iba.bussiness.calendar.creator.type.recurrence.RecurrenceCalendarTemplate;
import by.iba.bussiness.calendar.creator.type.single.SimpleCalendarTemplate;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussiness.meeting.wrapper.model.complex.ComplexMeetingWrapper;
import by.iba.bussiness.meeting.wrapper.model.reccurence.RecurrenceMeetingWrapper;
import by.iba.bussiness.meeting.wrapper.model.single.SingleMeetingWrapper;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CalendarFactory {
    private SimpleCalendarTemplate simpleCalendarTemplate;
    private RecurrenceCalendarTemplate recurrenceCalendarTemplate;
    private ComplexCalendarTemplate complexCalendarTemplate;

    @Autowired
    public CalendarFactory(SimpleCalendarTemplate simpleCalendarTemplate,
                           RecurrenceCalendarTemplate recurrenceCalendarTemplate,
                           ComplexCalendarTemplate complexCalendarTemplate) {
        this.simpleCalendarTemplate = simpleCalendarTemplate;
        this.recurrenceCalendarTemplate = recurrenceCalendarTemplate;
        this.complexCalendarTemplate = complexCalendarTemplate;
    }

    public <T extends MeetingWrapper> Calendar createInvitationCalendarTemplate(T wrapper, HttpServletRequest request, Meeting meeting) {
        Calendar calendar = null;
        switch (wrapper.getMeetingType()) {
            case SINGLE:
                SingleMeetingWrapper singleMeetingWrapper = ((SingleMeetingWrapper) wrapper);
                calendar = simpleCalendarTemplate.createSingleMeetingInvitationTemplate(singleMeetingWrapper, meeting);
                break;
            case RECURRENCE:
                RecurrenceMeetingWrapper recurrenceMeetingWrapper = ((RecurrenceMeetingWrapper) wrapper);
                calendar = recurrenceCalendarTemplate.createRecurrenceCalendarInvitationTemplate(recurrenceMeetingWrapper, request, meeting);
                break;
            case COMPLEX:
                ComplexMeetingWrapper complexMeetingWrapper = ((ComplexMeetingWrapper) wrapper);
                calendar = complexCalendarTemplate.createComplexCalendarInvitationTemplate(complexMeetingWrapper, request);
                break;
        }
        return calendar;
    }
}

