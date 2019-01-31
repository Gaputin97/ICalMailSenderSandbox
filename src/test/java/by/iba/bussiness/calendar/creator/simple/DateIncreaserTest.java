package by.iba.bussiness.calendar.creator.simple;

import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.session.SessionConstants;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class DateIncreaserTest {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(SessionConstants.DATE_FORMAT).withZone(ZoneId.of("UTC"));
    private DateIncreaser dateIncreaser = new DateIncreaser();

    @Test
    public void testIncreaseDateWhenFrequencyIsMinutely() {
        Instant nowDate = Instant.now();
        Instant expectedInstantDate = nowDate.plus(3, ChronoUnit.MINUTES);
        String expectedDate = dateFormat.format(expectedInstantDate);

        String actualDate = dateIncreaser.increaseDate(Frequency.MINUTELY, 3, nowDate);

        assertEquals("Increased dates doesn't matches", expectedDate, actualDate);
    }

    @Test
    public void testIncreaseDateWhenFrequencyIsHourly() {
        Instant nowDate = Instant.now();
        Instant expectedInstantDate = nowDate.plus(3, ChronoUnit.HOURS);
        String expectedDate = dateFormat.format(expectedInstantDate);

        String actualDate = dateIncreaser.increaseDate(Frequency.HOURLY, 3, nowDate);

        assertEquals("Increased dates doesn't matches", expectedDate, actualDate);
    }

    @Test
    public void testIncreaseDateWhenFrequencyIsDaily() {
        Instant nowDate = Instant.now();
        Instant expectedInstantDate = nowDate.plus(3, ChronoUnit.DAYS);
        String expectedDate = dateFormat.format(expectedInstantDate);

        String actualDate = dateIncreaser.increaseDate(Frequency.DAILY, 3, nowDate);

        assertEquals("Increased dates doesn't matches",expectedDate, actualDate);
    }

    @Test
    public void testIncreaseDateWhenFrequencyIsWeekly() {
        Instant nowDate = Instant.now();
        Instant expectedInstantDate = nowDate.plus(14, ChronoUnit.DAYS);
        String expectedDate = dateFormat.format(expectedInstantDate);

        String actualDate = dateIncreaser.increaseDate(Frequency.WEEKLY, 2, nowDate);

        assertEquals("Increased dates doesn't matches",expectedDate, actualDate);
    }
}