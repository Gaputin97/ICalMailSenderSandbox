package by.iba.bussiness.calendar.creator.recurrence;

import by.iba.bussiness.facade.SimpleCalendarSenderFacade;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.CalendarException;
import net.fortuna.ical4j.model.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@org.springframework.stereotype.Component
public class RecurrenceMeetingCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCalendarSenderFacade.class);
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
        try {
            return new Calendar(calendar);
        } catch (ParseException | IOException | URISyntaxException e) {
            logger.error("Can't create calendar based on another calendar", e);
            throw new CalendarException("Can't create calendar.");
        }
    }
}