package by.iba.bussiness.calendar.rrule.interval;

import by.iba.bussiness.calendar.rrule.frequence.model.RruleFreqType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class IntervalDefiner {

    private IntervalHelper intervalHelper;

    @Autowired
    public IntervalDefiner(IntervalHelper intervalHelper) {
        this.intervalHelper = intervalHelper;
    }

    public long defineInterval(List<Date> startDatesOfSessions, RruleFreqType rruleFreqType) {
        final int amountOfDurationsBetweenDates = startDatesOfSessions.size() - 1;
        long minimumInterval = rruleFreqType.getMillisecondsInOneHundredYear();
        for (int numberOfDates = 0; numberOfDates < amountOfDurationsBetweenDates; numberOfDates++) {
            long timeBetweenSessions = startDatesOfSessions.get(numberOfDates + 1).getTime() - startDatesOfSessions.get(numberOfDates).getTime();
            minimumInterval = intervalHelper.defineInterval(rruleFreqType, timeBetweenSessions, minimumInterval);
        }
        return minimumInterval;
    }
}
