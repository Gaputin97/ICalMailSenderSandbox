package by.iba.bussiness.calendar.factory;

import by.iba.bussiness.calendar.creator.type.complex.ComplexCalendarTemplateCreator;
import by.iba.bussiness.calendar.creator.type.recurrence.RecurrenceCalendarTemplateCreator;
import by.iba.bussiness.calendar.creator.type.single.SimpleCalendarTemplateCreator;
import by.iba.bussiness.calendar.date.model.single.SingleDateHelper;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.date.model.complex.ComplexDateHelper;
import by.iba.bussiness.calendar.date.model.reccurence.RecurrenceDateHelper;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarFactory {
    private SimpleCalendarTemplateCreator simpleCalendarTemplateCreator;
    private RecurrenceCalendarTemplateCreator recurrenceCalendarTemplateCreator;
    private ComplexCalendarTemplateCreator complexCalendarTemplateCreator;

    @Autowired
    public CalendarFactory(SimpleCalendarTemplateCreator simpleCalendarTemplateCreator,
                           RecurrenceCalendarTemplateCreator recurrenceCalendarTemplateCreator,
                           ComplexCalendarTemplateCreator complexCalendarTemplateCreator) {
        this.simpleCalendarTemplateCreator = simpleCalendarTemplateCreator;
        this.recurrenceCalendarTemplateCreator = recurrenceCalendarTemplateCreator;
        this.complexCalendarTemplateCreator = complexCalendarTemplateCreator;
    }

    public <T extends DateHelper> Calendar createInvitationCalendarTemplate(T wrapper, Meeting meeting) {
        Calendar calendar = null;
        switch (wrapper.getMeetingType()) {
            case SINGLE:
                SingleDateHelper singleDateHelper = ((SingleDateHelper) wrapper);
                calendar = simpleCalendarTemplateCreator.createSimpleMeetingInvitationTemplate(singleDateHelper, meeting);
                break;
            case RECURRENCE:
                RecurrenceDateHelper recurrenceDateHelper = ((RecurrenceDateHelper) wrapper);
                calendar = recurrenceCalendarTemplateCreator.createRecurrenceCalendarInvitationTemplate(recurrenceDateHelper, meeting);
                break;
            case COMPLEX:
                ComplexDateHelper complexDateHelper = ((ComplexDateHelper) wrapper);
                calendar = complexCalendarTemplateCreator.createComplexCalendarInvitationTemplate(complexDateHelper, meeting);
                break;
        }
        return calendar;
    }
}

