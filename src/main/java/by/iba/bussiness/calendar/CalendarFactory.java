package by.iba.bussiness.calendar;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.calendar.creator.complex.ComplexMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.creator.recurrence.RecurrenceMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.creator.simple.SingleMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.date.helper.model.DateHelper;
import by.iba.bussiness.calendar.date.helper.model.reccurence.RecurrenceDateHelper;
import by.iba.bussiness.calendar.date.helper.model.single.SingleDateHelper;
import by.iba.bussiness.enrollment.Enrollment;
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
        this.complexMeetingCalendarTemplateCreator = complexMeetingCalendarTemplateCreator;
        this.singleMeetingCalendarTemplateCreator = singleMeetingCalendarTemplateCreator;
        this.recurrenceMeetingCalendarTemplateCreator = recurrenceMeetingCalendarTemplateCreator;
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

        }
        return calendar;
    }

    public <T extends DateHelper> Calendar createCancelCalendarTemplate(T helper, Appointment newAppointment, Enrollment enrollment) {
        Calendar calendar = null;
        switch (helper.getMeetingType()) {
            case SINGLE:
                SingleDateHelper singleDateHelper = ((SingleDateHelper) helper);
                calendar = singleMeetingCalendarTemplateCreator.createSingleMeetingCancellationTemplate(singleDateHelper, newAppointment, enrollment);
                break;
            case RECURRENCE:
                RecurrenceDateHelper recurrenceDateHelper = ((RecurrenceDateHelper) helper);
                calendar = recurrenceMeetingCalendarTemplateCreator.createRecurrenceCalendarCancellationTemplate(recurrenceDateHelper, newAppointment, enrollment);
                break;
        }
        return calendar;
    }
}

