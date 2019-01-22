package by.iba.bussiness.calendar.creator.simple;

import by.iba.bussiness.facade.SimpleCalendarSenderFacade;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.CalendarException;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

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

    public Calendar createSimpleCalendarTemplate(VEvent event) {
        return createCommonSimpleTemplate(event, requestCalendar);
    }

    public Calendar createSimpleCancellationCalendarWithEvent(VEvent event) {
        Property rruleProperty = event.getProperties().getProperty(Property.RRULE);
        Property exdateProperty = event.getProperties().getProperty(Property.EXDATE);
        event.getProperties().remove(rruleProperty);
        event.getProperties().remove(exdateProperty);
        return createCommonSimpleTemplate(event, cancelCalendar);
    }

    private Calendar createCommonSimpleTemplate(VEvent event,
                                                Calendar concreteCalendar) {
        try {
            Calendar calendar = new Calendar(concreteCalendar);
            calendar.getComponents().add(event);
            return calendar;
        } catch (ParseException | IOException | URISyntaxException e) {
            logger.error("Can't create calendar based on vEvent", e);
            throw new CalendarException("Can't create calendar.");
        }
    }
}