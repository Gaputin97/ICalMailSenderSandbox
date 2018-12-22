package by.iba.bussiness.calendar.rrule.model;

import by.iba.bussiness.calendar.rrule.frequence.model.RruleFreqType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Rrule {
    private RruleFreqType rruleFreqType;
    private Long interval;
    private List<Date> exDates = new ArrayList<>();

    public Rrule() {
    }

    public RruleFreqType getRruleFreqType() {
        return rruleFreqType;
    }

    public void setRruleFreqType(RruleFreqType rruleFreqType) {
        this.rruleFreqType = rruleFreqType;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public List<Date> getExDates() {
        return exDates;
    }

    public void setExDates(List<Date> exDates) {
        this.exDates = exDates;
    }
}
