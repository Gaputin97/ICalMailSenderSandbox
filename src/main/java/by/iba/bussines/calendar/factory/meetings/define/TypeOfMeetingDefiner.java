package by.iba.bussines.calendar.factory.meetings.define;

import by.iba.bussines.calendar.factory.meetings.define.complex.ComplexCalendarInvitationTemplate;
import by.iba.bussines.calendar.factory.meetings.define.recurrence.RecurrenceCalendarInvitationTemplate;
import by.iba.bussines.calendar.factory.meetings.define.single.SimpleCalendarInvitationTemplate;
import by.iba.bussines.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussines.meeting.wrapper.model.complex.ComplexMeetingWrapper;
import by.iba.bussines.meeting.wrapper.model.reccurence.RecurrenceMeetingWrapper;
import by.iba.bussines.meeting.wrapper.model.single.SingleMeetingWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TypeOfMeetingDefiner {
    private SimpleCalendarInvitationTemplate simpleCalendarInvitationTemplate;
    private RecurrenceCalendarInvitationTemplate recurrenceCalendarInvitationTemplate;
    private ComplexCalendarInvitationTemplate complexCalendarInvitationTemplate;

    @Autowired
    public TypeOfMeetingDefiner(SimpleCalendarInvitationTemplate simpleCalendarInvitationTemplate,
                                RecurrenceCalendarInvitationTemplate recurrenceCalendarInvitationTemplate,
                                ComplexCalendarInvitationTemplate complexCalendarInvitationTemplate) {
        this.simpleCalendarInvitationTemplate = simpleCalendarInvitationTemplate;
        this.recurrenceCalendarInvitationTemplate = recurrenceCalendarInvitationTemplate;
        this.complexCalendarInvitationTemplate = complexCalendarInvitationTemplate;
    }

    public <T extends MeetingWrapper> void createSingleMeetingInvitationTemplate(T wrapper) {
        switch (wrapper.getMeetingType()) {
            case SINGLE:
                SingleMeetingWrapper singleMeetingWrapper = ((SingleMeetingWrapper) wrapper);
                simpleCalendarInvitationTemplate.createSingleMeetingInvitationTemplate(singleMeetingWrapper);
                break;
            case RECURRENCE:
                RecurrenceMeetingWrapper recurrenceMeetingWrapper = ((RecurrenceMeetingWrapper) wrapper);
                recurrenceCalendarInvitationTemplate.createRecurrenceCalendarInvitationTemplate(recurrenceMeetingWrapper);
                break;
            case COMPLEX:
                ComplexMeetingWrapper complexMeetingWrapper = ((ComplexMeetingWrapper) wrapper);
                complexCalendarInvitationTemplate.createComplexCalendarInvitationTemplate(complexMeetingWrapper);
                break;
        }
    }
}

