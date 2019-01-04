package by.iba.bussiness.calendar;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.creator.complex.ComplexMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.creator.recurrence.RecurrenceMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.creator.single.SimpleMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.date.model.single.SingleDateHelper;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.date.model.complex.ComplexDateHelper;
import by.iba.bussiness.calendar.date.model.reccurence.RecurrenceDateHelper;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarFactory {
    private SimpleMeetingCalendarTemplateCreator simpleMeetingCalendarTemplateCreator;
    private RecurrenceMeetingCalendarTemplateCreator recurrenceMeetingCalendarTemplateCreator;
    private ComplexMeetingCalendarTemplateCreator complexMeetingCalendarTemplateCreator;

    @Autowired
    public CalendarFactory(SimpleMeetingCalendarTemplateCreator simpleMeetingCalendarTemplateCreator,
                           RecurrenceMeetingCalendarTemplateCreator recurrenceMeetingCalendarTemplateCreator,
                           ComplexMeetingCalendarTemplateCreator complexMeetingCalendarTemplateCreator) {
        this.simpleMeetingCalendarTemplateCreator = simpleMeetingCalendarTemplateCreator;
        this.recurrenceMeetingCalendarTemplateCreator = recurrenceMeetingCalendarTemplateCreator;
        this.complexMeetingCalendarTemplateCreator = complexMeetingCalendarTemplateCreator;
    }

    public <T extends DateHelper> Calendar createInvitationCalendarTemplate(T helper, Appointment appointment, Enrollment enrollment) {
        Calendar calendar = null;
        switch (helper.getMeetingType()) {
            case SINGLE:
                SingleDateHelper singleDateHelper = ((SingleDateHelper) helper);
                calendar = simpleMeetingCalendarTemplateCreator.createSimpleMeetingInvitationTemplate(singleDateHelper, appointment, enrollment);
                break;
            case RECURRENCE:
                RecurrenceDateHelper recurrenceDateHelper = ((RecurrenceDateHelper) helper);
                calendar = recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarInvitationTemplate(recurrenceDateHelper, appointment, enrollment);
                break;
            case COMPLEX:
                ComplexDateHelper complexDateHelper = ((ComplexDateHelper) helper);
                calendar = complexMeetingCalendarTemplateCreator.createComplexCalendarInvitationTemplate(complexDateHelper, appointment, enrollment);
                break;
        }
        return calendar;
    }

    public <T extends DateHelper> Calendar createCancelCalendarTemplate(T helper, Appointment appointment, Enrollment enrollment) {
        Calendar calendar = null;
        switch (helper.getMeetingType()) {
            case SINGLE:
                SingleDateHelper singleDateHelper = ((SingleDateHelper) helper);
                calendar = simpleMeetingCalendarTemplateCreator.createSimpleMeetingCancellationTemplate(singleDateHelper, appointment, enrollment);
                break;
            case RECURRENCE:
                RecurrenceDateHelper recurrenceDateHelper = ((RecurrenceDateHelper) helper);
                calendar = recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarCancellationTemplate(recurrenceDateHelper, appointment, enrollment);
                break;
            case COMPLEX:
                ComplexDateHelper complexDateHelper = ((ComplexDateHelper) helper);
                calendar = complexMeetingCalendarTemplateCreator.createComplexCalendarInvitationTemplate(complexDateHelper, appointment, enrollment);
                break;
        }
        return calendar;
    }
}

