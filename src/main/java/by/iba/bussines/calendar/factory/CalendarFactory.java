package by.iba.bussines.calendar.factory;

import by.iba.bussines.calendar.creator.type.complex.ComplexCalendarInvitationTemplate;
import by.iba.bussines.calendar.creator.type.recurrence.RecurrenceCalendarInvitationTemplate;
import by.iba.bussines.calendar.creator.type.single.SimpleCalendarInvitationTemplate;
import by.iba.bussines.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussines.meeting.wrapper.model.complex.ComplexMeetingWrapper;
import by.iba.bussines.meeting.wrapper.model.reccurence.RecurrenceMeetingWrapper;
import by.iba.bussines.meeting.wrapper.model.single.SingleMeetingWrapper;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CalendarFactory {
    private SimpleCalendarInvitationTemplate simpleCalendarInvitationTemplate;
    private RecurrenceCalendarInvitationTemplate recurrenceCalendarInvitationTemplate;
    private ComplexCalendarInvitationTemplate complexCalendarInvitationTemplate;

    @Autowired
    public CalendarFactory(SimpleCalendarInvitationTemplate simpleCalendarInvitationTemplate,
                           RecurrenceCalendarInvitationTemplate recurrenceCalendarInvitationTemplate,
                           ComplexCalendarInvitationTemplate complexCalendarInvitationTemplate) {
        this.simpleCalendarInvitationTemplate = simpleCalendarInvitationTemplate;
        this.recurrenceCalendarInvitationTemplate = recurrenceCalendarInvitationTemplate;
        this.complexCalendarInvitationTemplate = complexCalendarInvitationTemplate;
    }

    public <T extends MeetingWrapper> Calendar createInvitationCalendarTemplate(T wrapper, HttpServletRequest request) {
        Calendar calendar = null;
        switch (wrapper.getMeetingType()) {
            case SINGLE:
                SingleMeetingWrapper singleMeetingWrapper = ((SingleMeetingWrapper) wrapper);
                calendar = simpleCalendarInvitationTemplate.createSingleMeetingInvitationTemplate(singleMeetingWrapper, request);
                break;
            case RECURRENCE:
                RecurrenceMeetingWrapper recurrenceMeetingWrapper = ((RecurrenceMeetingWrapper) wrapper);
                calendar = recurrenceCalendarInvitationTemplate.createRecurrenceCalendarInvitationTemplate(recurrenceMeetingWrapper, request);
                break;
            case COMPLEX:
                ComplexMeetingWrapper complexMeetingWrapper = ((ComplexMeetingWrapper) wrapper);
                calendar = complexCalendarInvitationTemplate.createComplexCalendarInvitationTemplate(complexMeetingWrapper, request);
                break;
        }
        return calendar;
    }
}

