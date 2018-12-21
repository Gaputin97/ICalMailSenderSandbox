package by.iba.bussiness.calendar.creator.set_upper;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BasicCalendarTemplateSetUpper {
    private static final String PRODUCT_IDENTIFIER = "-//Your Learning//EN";

    @Bean("requestCalendar")
    public Calendar setUpRequestMethod() {
        Calendar calendar = setUpCommonFields();
        calendar.getProperties().add(Method.REQUEST);
        return calendar;
    }

    @Bean("publishCalendar")
    public Calendar setUpPublishMethod() {
        Calendar calendar = setUpCommonFields();
        calendar.getProperties().add(Method.PUBLISH);
        return calendar;
    }

    @Bean("cancelCalendar")
    public Calendar setUpCancelMethod() {
        Calendar calendar = setUpCommonFields();
        calendar.getProperties().add(Method.CANCEL);
        return calendar;
    }

    private Calendar setUpCommonFields() {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        calendar.getProperties().add(new ProdId(PRODUCT_IDENTIFIER));
        return calendar;
    }
}
