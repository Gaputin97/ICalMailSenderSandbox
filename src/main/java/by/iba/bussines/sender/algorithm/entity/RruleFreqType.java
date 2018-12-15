package by.iba.bussines.sender.algorithm.entity;

import java.util.Calendar;

public enum RruleFreqType {
    MINUTELY(Calendar.MINUTE), HOURLY(Calendar.HOUR), DAILY(Calendar.DATE), WEEKLY(Calendar.WEEK_OF_MONTH);
    private int frequence;

    RruleFreqType(int frequence) {
        this.frequence = frequence;
    }

    public int getFrequence() {
        return frequence;
    }

}
