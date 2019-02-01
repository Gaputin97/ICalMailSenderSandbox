package by.iba.bussiness.calendar.rrule.frequence;

import org.junit.Test;

import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class FrequencyHelperTest {
    private FrequencyHelper frequencyHelper = new FrequencyHelper();

    @Test
    public void testWhenDurationIsMultipleToHourlyFrequency() {
        Frequency frequency = Frequency.HOURLY;
        long millisecondsBetween5Hours = 18_000_000L;

        boolean actualIsDurationMultipleToFrequency = frequencyHelper.isDurationMultipleToFreq(frequency, millisecondsBetween5Hours);

        assertTrue(actualIsDurationMultipleToFrequency);
    }

    @Test
    public void testWhenDurationIsNotMultipleToHourlyFrequency() {
        Frequency frequency = Frequency.HOURLY;
        long customMillisecondsBetweenSessions = 18_111_111L;

        boolean actualIsDurationMultipleToFrequency = frequencyHelper.isDurationMultipleToFreq(frequency, customMillisecondsBetweenSessions);

        assertFalse(actualIsDurationMultipleToFrequency);
    }


    @Test
    public void testWhenDurationIsMultipleToDailyFrequency() {
        Frequency frequency = Frequency.DAILY;
        long millisecondsIn5Days = 432_000_000L;

        boolean actualIsDurationMultipleToFrequency = frequencyHelper.isDurationMultipleToFreq(frequency, millisecondsIn5Days);

        assertTrue(actualIsDurationMultipleToFrequency);
    }

    @Test
    public void testWhenDurationIsNotMultipleToDailyFrequency() {
        Frequency frequency = Frequency.DAILY;
        long customMillisecondsBetweenSessions = 432_111_111L;

        boolean actualIsDurationMultipleToFrequency = frequencyHelper.isDurationMultipleToFreq(frequency, customMillisecondsBetweenSessions);

        assertFalse(actualIsDurationMultipleToFrequency);
    }

    @Test
    public void testWhenDurationIsMultipleToWeeklyFrequency() {
        Frequency frequency = Frequency.WEEKLY;
        long millisecondsIn5Weeks = 3_024_000_000L;

        boolean actualIsDurationMultipleToFrequency = frequencyHelper.isDurationMultipleToFreq(frequency, millisecondsIn5Weeks);

        assertTrue(actualIsDurationMultipleToFrequency);
    }

    @Test
    public void testWhenDurationIsNotMultipleToWeeklyFrequency() {
        Frequency frequency = Frequency.WEEKLY;
        long customMillisecondsBetweenSessions = 3_024_111_111L;

        boolean actualIsDurationMultipleToFrequency = frequencyHelper.isDurationMultipleToFreq(frequency, customMillisecondsBetweenSessions);

        assertFalse(actualIsDurationMultipleToFrequency);
    }
}