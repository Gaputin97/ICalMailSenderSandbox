package by.iba.bussiness.calendar.creator.simple;

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
public class SimpleMetingCalendarTemplateCreator {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCalendarSenderFacade.class);
    private Calendar requestCalendar;
    private Calendar cancelCalendar;

    @Autowired
    public SimpleMetingCalendarTemplateCreator(@Qualifier("requestCalendar") Calendar requestCalendar,
                                               @Qualifier("cancelCalendar") Calendar cancelCalendar) {
        this.requestCalendar = requestCalendar;
        this.cancelCalendar = cancelCalendar;
    }

    public Calendar createSimpleCalendarTemplate(Calendar calendar) {
        return createCommonRecurrenceTemplate(calendar, requestCalendar);
    }

    public Calendar createSimpleCancellationCalendarTemplate(Calendar calendar) {
        return createCommonRecurrenceTemplate(calendar, cancelCalendar);

    }

    private Calendar createCommonRecurrenceTemplate(Calendar calendar,
                                                    Calendar concreteCalendar) {
        List<Property> concreteCalendarProperties = concreteCalendar.getProperties();
        try {
            Calendar newCalendar = new Calendar(calendar);
            newCalendar.getProperties().addAll(concreteCalendarProperties);
            return newCalendar;
        } catch (ParseException | IOException | URISyntaxException e) {
            logger.error("Can't create calendar based on another calendar", e);
            throw new CalendarException("Can't create calendar.");
        }
    }
}