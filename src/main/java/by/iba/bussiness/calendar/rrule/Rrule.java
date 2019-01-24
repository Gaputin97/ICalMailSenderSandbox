package by.iba.bussiness.calendar.rrule;

import by.iba.bussiness.calendar.rrule.frequence.Frequency;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Rrule {
    private Frequency frequency;
    private long interval;
    private List<Instant> exDates;
    private Count count;


    public Rrule() {
        exDates = new ArrayList<>();
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

    public List<Instant> getExDates() {
        return exDates;
    }

    public void setExDates(List<Instant> exDates) {
        this.exDates = exDates;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public Count getCount() {
        return count;
    }

    public void setCount(Count count) {
        this.count = count;
    }
}
