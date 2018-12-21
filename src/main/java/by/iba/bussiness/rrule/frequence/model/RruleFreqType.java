package by.iba.bussiness.rrule.frequence.model;

import by.iba.bussiness.rrule.constants.DateConstants;

import java.util.Calendar;

public enum RruleFreqType {
    MINUTELY(Calendar.MINUTE, DateConstants.MILLISECONDS_IN_MINUTE, DateConstants.MINUTES_IN_ONE_HUNDRED_YEARS),
    HOURLY(Calendar.HOUR, DateConstants.MILLISECONDS_IN_HOUR, DateConstants.HOURS_IN_ONE_HUNDRED_YEARS),
    DAILY(Calendar.DATE, DateConstants.MILLISECONDS_IN_DAY, DateConstants.DAYS_IN_ONE_HUNDRED_YEARS),
    WEEKLY(Calendar.WEEK_OF_MONTH, DateConstants.MILLISECONDS_IN_WEEK, DateConstants.WEEKS_IN_ONE_HUNDRED_YEARS);

    private int frequence;
    private long millisecondsInFreq;
    private long millisecondsInOneHundredYear;

    RruleFreqType(int frequence, long millisecondsInFreq, long millisecondsInOneHundredYear) {
        this.frequence = frequence;
        this.millisecondsInFreq = millisecondsInFreq;
        this.millisecondsInOneHundredYear = millisecondsInOneHundredYear;
    }

    public int getFrequence() {
        return frequence;
    }

    public long getMillisecondsInFreq() {
        return millisecondsInFreq;
    }

    public long getMillisecondsInOneHundredYear() {
        return millisecondsInOneHundredYear;
    }
}
