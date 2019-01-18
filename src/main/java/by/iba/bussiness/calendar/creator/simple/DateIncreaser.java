package by.iba.bussiness.calendar.creator.simple;

import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.session.SessionConstants;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@Component
public class DateIncreaser {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(SessionConstants.DATE_FORMAT).withZone(ZoneId.of("UTC"));

    public String increaseAndParse(Frequency frequency, long interval, Instant date) {
        long milliseconds = date.toEpochMilli();
        milliseconds += frequency.getMillisecondsInFreq() * interval;
        Instant instant = Instant.ofEpochMilli(milliseconds);
        return dateFormat.format(instant);
    }
}
