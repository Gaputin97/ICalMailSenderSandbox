package by.iba.bussiness.calendar.rrule.interval;

import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class IntervalDefiner {
    @Autowired
    private IntervalHelper intervalHelper;

    public long defineInterval(List<Instant> startDatesOfSessions, Frequency frequency) {
        int amountOfDurationsBetweenDates = startDatesOfSessions.size() - 1;
        long minimumInterval = frequency.getMillisecondsInOneHundredYear();
        for (int numberOfDates = 0; numberOfDates < amountOfDurationsBetweenDates; numberOfDates++) {
            long timeBetweenSessions = startDatesOfSessions.get(numberOfDates + 1).toEpochMilli() - startDatesOfSessions.get(numberOfDates).toEpochMilli();
            minimumInterval = intervalHelper.defineInterval(frequency, timeBetweenSessions, minimumInterval);
        }
        return minimumInterval;
    }
}
