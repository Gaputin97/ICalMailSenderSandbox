package by.iba.bussiness.calendar.creator.recurrence;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@org.springframework.stereotype.Component
public class RecurrenceMeetingCalendarTemplateCreator {
    private Calendar requestCalendar;
    private Calendar cancelCalendar;

    @Autowired
    public RecurrenceMeetingCalendarTemplateCreator(@Qualifier("requestCalendar") Calendar requestCalendar,
                                                    @Qualifier("cancelCalendar") Calendar cancelCalendar) {
        this.requestCalendar = requestCalendar;
        this.cancelCalendar = cancelCalendar;
    }

    public Calendar createRecurrenceCalendarInvitationTemplate(Calendar calendar) {
        return createCommonRecurrenceTemplate(calendar, requestCalendar);
    }

    public Calendar createRecurrenceCalendarCancellationTemplate(Calendar calendar) {
        return createCommonRecurrenceTemplate(calendar, cancelCalendar);

    }

    private Calendar createCommonRecurrenceTemplate(Calendar calendar,
                                                    Calendar concreteCalendar) {
        List<Property> concreteCalendarProperties = concreteCalendar.getProperties();
        calendar.getProperties().addAll(concreteCalendarProperties);
        return calendar;
    }
}