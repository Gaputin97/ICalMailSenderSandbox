package by.iba.bussines.rrule.interval;

import by.iba.bussines.rrule.constants.DateConstants;
import by.iba.bussines.rrule.frequence.model.RruleFreqType;

public class IntervalHelper {

    public long defineInterval(RruleFreqType rruleFreqType, long timeBetweenSessions, long minimumInterval) {
        long millisecondsInFreq = rruleFreqType.getMillisecondsInFreq();
        long freqsInOneHundredYears = rruleFreqType.getMillisecondsInOneHundredYear();
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
