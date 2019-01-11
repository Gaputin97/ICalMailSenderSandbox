package by.iba.bussiness.calendar;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.creator.complex.ComplexMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.creator.recurrence.RecurrenceMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.creator.simple.SingleMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.date.model.single.SingleDateHelper;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.date.model.complex.ComplexDateHelper;
import by.iba.bussiness.calendar.date.model.reccurence.RecurrenceDateHelper;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalendarFactory {
    private SingleMeetingCalendarTemplateCreator singleMeetingCalendarTemplateCreator;
    private RecurrenceMeetingCalendarTemplateCreator recurrenceMeetingCalendarTemplateCreator;
    private ComplexMeetingCalendarTemplateCreator complexMeetingCalendarTemplateCreator;

    @Autowired
    public CalendarFactory(SingleMeetingCalendarTemplateCreator singleMeetingCalendarTemplateCreator,
                           RecurrenceMeetingCalendarTemplateCreator recurrenceMeetingCalendarTemplateCreator,
                           ComplexMeetingCalendarTemplateCreator complexMeetingCalendarTemplateCreator) {
        this.singleMeetingCalendarTemplateCreator = singleMeetingCalendarTemplateCreator;
        this.recurrenceMeetingCalendarTemplateCreator = recurrenceMeetingCalendarTemplateCreator;
        this.complexMeetingCalendarTemplateCreator = complexMeetingCalendarTemplateCreator;
    }

    public <T extends DateHelper> Calendar createCalendarTemplate(T helper, Appointment appointment, Enrollment enrollment) {
        Calendar calendar = null;
        switch (helper.getMeetingType()) {
            case SINGLE:
                SingleDateHelper singleDateHelper = ((SingleDateHelper) helper);
                calendar = singleMeetingCalendarTemplateCreator.createSingleMeetingInvitationTemplate(singleDateHelper, appointment, enrollment);
                break;
            case RECURRENCE:
                RecurrenceDateHelper recurrenceDateHelper = ((RecurrenceDateHelper) helper);
                calendar = recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarInvitationTemplate(recurrenceDateHelper, appointment, enrollment);
                break;
            case COMPLEX:
                ComplexDateHelper complexDateHelper = ((ComplexDateHelper) helper);
                calendar = complexMeetingCalendarTemplateCreator.createInitialComplexCalendarTemplate(complexDateHelper, appointment, enrollment);
                break;
        }
        return calendar;
    }

    public <T extends DateHelper> Calendar createCancelCalendarTemplate(T helper, Appointment appointment, Enrollment enrollment) {
        Calendar calendar = null;
        switch (helper.getMeetingType()) {
            case SINGLE:
                SingleDateHelper singleDateHelper = ((SingleDateHelper) helper);
                calendar = singleMeetingCalendarTemplateCreator.createSingleMeetingCancellationTemplate(singleDateHelper, appointment, enrollment);
                break;
            case RECURRENCE:
                RecurrenceDateHelper recurrenceDateHelper = ((RecurrenceDateHelper) helper);
                calendar = recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarCancellationTemplate(recurrenceDateHelper, appointment, enrollment);
                break;
            case COMPLEX:
                ComplexDateHelper complexDateHelper = ((ComplexDateHelper) helper);
                calendar = complexMeetingCalendarTemplateCreator.createInitialComplexCalendarTemplate(complexDateHelper, appointment, enrollment);
                break;
        }
        return calendar;
    }

    public Calendar createInitialCalendarTemplate(ComplexDateHelper complexDateHelper, Appointment oldAppointment, Enrollment enrollment) {
        Calendar calendar = complexMeetingCalendarTemplateCreator.createInitialComplexCalendarTemplate();
        return calendar;
    }

    public Calendar createUpdateCalendarTemplate(ComplexDateHelper complexDateHelper, Appointment oldAppointment, Appointment newAppointment, Enrollment enrollment) {
        Calendar calendar = complexMeetingCalendarTemplateCreator.createUpdateComplexCalendarTemplate();
        return  calendar;
    }
}

