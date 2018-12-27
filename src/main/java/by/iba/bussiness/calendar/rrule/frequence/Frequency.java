package by.iba.bussiness.calendar.rrule.frequence;

import by.iba.bussiness.calendar.rrule.constants.DateConstants;

import java.util.Calendar;

public enum Frequency {
    MINUTELY(Calendar.MINUTE, DateConstants.MILLISECONDS_IN_MINUTE, DateConstants.MINUTES_IN_ONE_HUNDRED_YEARS),
    HOURLY(Calendar.HOUR, DateConstants.MILLISECONDS_IN_HOUR, DateConstants.HOURS_IN_ONE_HUNDRED_YEARS),
    DAILY(Calendar.DATE, DateConstants.MILLISECONDS_IN_DAY, DateConstants.DAYS_IN_ONE_HUNDRED_YEARS),
    WEEKLY(Calendar.WEEK_OF_MONTH, DateConstants.MILLISECONDS_IN_WEEK, DateConstants.WEEKS_IN_ONE_HUNDRED_YEARS);

    private int frequency;
    private long millisecondsInFreq;
    private long millisecondsInOneHundredYear;

    Frequency(int frequency, long millisecondsInFreq, long millisecondsInOneHundredYear) {
        this.frequency = frequency;
        this.millisecondsInFreq = millisecondsInFreq;
        this.millisecondsInOneHundredYear = millisecondsInOneHundredYear;
    }

    public int getFrequency() {
        return frequency;
    }

    public long getMillisecondsInFreq() {
        return millisecondsInFreq;
    }

    public long getMillisecondsInOneHundredYear() {
        return millisecondsInOneHundredYear;
    }
}
