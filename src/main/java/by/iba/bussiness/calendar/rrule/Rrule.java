package by.iba.bussiness.calendar.rrule;

import by.iba.bussiness.calendar.rrule.frequence.Frequency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Rrule {
    private Frequency frequency;
    private Long interval;
    private List<Date> exDates = new ArrayList<>();

    public Rrule() {
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
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
