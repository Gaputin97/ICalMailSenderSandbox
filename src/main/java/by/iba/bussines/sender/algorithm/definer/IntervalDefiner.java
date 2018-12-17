package by.iba.bussines.sender.algorithm.definer;


import by.iba.bussines.sender.algorithm.constants.DateConstants;
import by.iba.bussines.sender.algorithm.constants.EnumConstants;
import by.iba.bussines.sender.algorithm.entity.RruleFreqType;
import by.iba.bussines.sender.algorithm.wrappers.IntervalWrapper;

public class IntervalDefiner {

    public IntervalWrapper defineInterval(RruleFreqType rruleFreqType, long timeBetweenEvents, long minimumInterval) {
        IntervalWrapper intervalWrapper = new IntervalWrapper(false, minimumInterval);
        long millisecondsInFreq = 0;
        long freqsInOneHundredYears = 0;
        switch (rruleFreqType.ordinal()) {
            case EnumConstants.ORDINAL_OF_WEEKLY:        // REMAKE USING 63 PAGE OF CLEAN CODE
                millisecondsInFreq = DateConstants.MILLISECONDS_IN_WEEK;
                freqsInOneHundredYears = DateConstants.WEEKS_IN_ONE_HUNDRED_YEARS;
                break;
            case EnumConstants.ORDINAL_OF_DAILY:
                millisecondsInFreq = DateConstants.MILLISECONDS_IN_DAY;
                freqsInOneHundredYears = DateConstants.DAYS_IN_ONE_HUNDRED_YEARS;
                break;
            case EnumConstants.ORDINAL_OF_HOURLY:
                millisecondsInFreq = DateConstants.MILLISECONDS_IN_HOUR;
                freqsInOneHundredYears = DateConstants.HOURS_IN_ONE_HUNDRED_YEARS;
                break;
            case EnumConstants.ORDINAL_OF_MINUTELY:
                millisecondsInFreq = DateConstants.MILLISECONDS_IN_MINUTE;
                freqsInOneHundredYears = DateConstants.MINUTES_IN_ONE_HUNDRED_YEARS;
        }
        if (timeBetweenEvents % millisecondsInFreq == 0) {
            intervalWrapper.setDurationMultipleToFreq(true);
            if (!(minimumInterval == DateConstants.VALUE_FOR_DEFAULT_INTERVAL)) {
                long possibleInterval = timeBetweenEvents / millisecondsInFreq;
                if (possibleInterval < minimumInterval) {
                    if (minimumInterval % possibleInterval == 0 || minimumInterval == freqsInOneHundredYears) {
                        intervalWrapper.setMinimumIntervalOfFreq(possibleInterval);
                    } else {
                        intervalWrapper.setMinimumIntervalOfFreq(DateConstants.VALUE_FOR_DEFAULT_INTERVAL);
                    }
                } else if (!((possibleInterval) % minimumInterval == 0)) {
                    intervalWrapper.setMinimumIntervalOfFreq(DateConstants.VALUE_FOR_DEFAULT_INTERVAL);
                }
            }
        }
        return intervalWrapper;
    }


}
