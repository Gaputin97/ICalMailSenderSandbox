package by.iba.bussiness.calendar.rrule.interval;

import by.iba.bussiness.calendar.rrule.constants.DateConstants;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import org.springframework.stereotype.Component;

@Component
public class IntervalHelper {

    public long defineInterval(Frequency frequency, long timeBetweenSessions, long minimumInterval) {
        long millisecondsInFreq = frequency.getMillisecondsInFreq();
        long freqsInOneHundredYears = frequency.getMillisecondsInOneHundredYear();
        if (!(minimumInterval == DateConstants.VALUE_FOR_DEFAULT_INTERVAL)) {
            long possibleInterval = timeBetweenSessions / millisecondsInFreq;
            if (possibleInterval < minimumInterval) {
                if (minimumInterval % possibleInterval == 0 || minimumInterval == freqsInOneHundredYears) {
                    minimumInterval = possibleInterval;
                } else {
                    minimumInterval = DateConstants.VALUE_FOR_DEFAULT_INTERVAL;
                }
            } else if (!((possibleInterval) % minimumInterval == 0)) {
                minimumInterval = DateConstants.VALUE_FOR_DEFAULT_INTERVAL;
            }
        }
        return minimumInterval;
    }
}
