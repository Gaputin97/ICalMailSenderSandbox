package by.iba.bussines.calendar.factory.template.calendar.generator;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class GenerateCalendarTemplate {
    private static final String PRODUCT_IDENTIFIER = "-//Your Learning//EN";

    @Bean("request")
    public Calendar generateRequestMethodCalendar() {
        Calendar requestCalendarTemplate = new Calendar();
        requestCalendarTemplate.getProperties().add(Version.VERSION_2_0);
        requestCalendarTemplate.getProperties().add(CalScale.GREGORIAN);
        requestCalendarTemplate.getProperties().add(new ProdId(PRODUCT_IDENTIFIER));
        requestCalendarTemplate.getProperties().add(Method.REQUEST);
        return requestCalendarTemplate;
    }

    @Bean("publish")
    public Calendar generatePublishMethodCalendar() {
        Calendar cancelCalendarTemplate = new Calendar();
        cancelCalendarTemplate.getProperties().add(Version.VERSION_2_0);
        cancelCalendarTemplate.getProperties().add(CalScale.GREGORIAN);
        cancelCalendarTemplate.getProperties().add(new ProdId(PRODUCT_IDENTIFIER));
        cancelCalendarTemplate.getProperties().add(Method.PUBLISH);
        return cancelCalendarTemplate;
    }

    @Bean("cancel")
    public Calendar generateCancelMethodCalendar() {
        Calendar publishCalendarTemplate = new Calendar();
        publishCalendarTemplate.getProperties().add(Version.VERSION_2_0);
        publishCalendarTemplate.getProperties().add(CalScale.GREGORIAN);
        publishCalendarTemplate.getProperties().add(new ProdId(PRODUCT_IDENTIFIER));
        publishCalendarTemplate.getProperties().add(Method.CANCEL);
        return publishCalendarTemplate;
    }
}
