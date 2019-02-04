package by.iba.bussiness.calendar.creator.simple;

import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.session.DatePattern;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class DateIncreaser {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DatePattern.DATE_FORMAT).withZone(ZoneId.of("UTC"));

    public String increaseDate(Frequency frequency, long interval, Instant date) {
        long milliseconds = date.toEpochMilli();
        milliseconds += frequency.getMillisecondsInFrequency() * interval;
        Instant instant = Instant.ofEpochMilli(milliseconds);
        return dateFormat.format(instant);
    }
}
