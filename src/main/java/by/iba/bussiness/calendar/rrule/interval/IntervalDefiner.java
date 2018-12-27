package by.iba.bussiness.calendar.rrule.interval;

import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class IntervalDefiner {
    @Autowired
    private IntervalHelper intervalHelper;

    public long defineInterval(List<Date> startDatesOfSessions, Frequency frequency) {
        int amountOfDurationsBetweenDates = startDatesOfSessions.size() - 1;
        long minimumInterval = frequency.getMillisecondsInOneHundredYear();
        for (int numberOfDates = 0; numberOfDates < amountOfDurationsBetweenDates; numberOfDates++) {
            long timeBetweenSessions = startDatesOfSessions.get(numberOfDates + 1).getTime() - startDatesOfSessions.get(numberOfDates).getTime();
            minimumInterval = intervalHelper.defineInterval(frequency, timeBetweenSessions, minimumInterval);
        }
        return minimumInterval;
    }
}
