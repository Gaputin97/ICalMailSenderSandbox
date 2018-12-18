package by.iba.bussines.rrule.interval;

import by.iba.bussines.rrule.frequence.model.RruleFreqType;

import java.util.Date;
import java.util.List;

public class IntervalDefiner {

    public long defineInterval(List<Date> startDatesOfSessions, RruleFreqType rruleFreqType) {
        final int amountOfDurationsBetweenDates = startDatesOfSessions.size() - 1;
        IntervalHelper timeHandler = new IntervalHelper();
        long minimumInterval = rruleFreqType.getMillisecondsInOneHundredYear();
        for (int numberOfDates = 0; numberOfDates < amountOfDurationsBetweenDates; numberOfDates++) {
            long timeBetweenSessions = startDatesOfSessions.get(numberOfDates + 1).getTime() - startDatesOfSessions.get(numberOfDates).getTime();
            minimumInterval = timeHandler.defineInterval(rruleFreqType, timeBetweenSessions, minimumInterval);
        }
        return minimumInterval;
    }
}
