package by.iba.bussiness.calendar.rrule.frequence.wrapper;

import by.iba.bussiness.calendar.rrule.frequence.model.Frequency;

public class FrequencyWrapper {

    private Frequency frequency;
    private int amountOfDurationMultipleToFreq;

    public FrequencyWrapper(Frequency frequency, int minimumDuration) {
        this.frequency = frequency;
        this.amountOfDurationMultipleToFreq = minimumDuration;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public int getAmountOfDurationMultipleToFreq() {
        return amountOfDurationMultipleToFreq;
    }

    public void setAmountOfDurationMultipleToFreq(int amountOfDurationMultipleToFreq) {
        this.amountOfDurationMultipleToFreq = amountOfDurationMultipleToFreq;
    }
}
