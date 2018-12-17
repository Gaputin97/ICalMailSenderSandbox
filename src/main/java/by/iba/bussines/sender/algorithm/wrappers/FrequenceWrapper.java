package by.iba.bussines.sender.algorithm.wrappers;


import by.iba.bussines.sender.algorithm.entity.RruleFreqType;

public class FrequenceWrapper {

    private RruleFreqType rruleFreqType;
    private int amountOfDurationMultipleToFreq;
    private long minimumIntervalOfFreq;

    public FrequenceWrapper(RruleFreqType rruleFreqType, int minimumDuration, long minimumIntervalOfFreq) {
        this.rruleFreqType = rruleFreqType;
        this.amountOfDurationMultipleToFreq = minimumDuration;
        this.minimumIntervalOfFreq = minimumIntervalOfFreq;
    }

    public long getMinimumIntervalOfFreq() {
        return minimumIntervalOfFreq;
    }

    public void setMinimumIntervalOfFreq(long minimumIntervalOfFreq) {
        this.minimumIntervalOfFreq = minimumIntervalOfFreq;
    }

    public RruleFreqType getRruleFreqType() {
        return rruleFreqType;
    }

    public void setRruleFreqType(RruleFreqType rruleFreqType) {
        this.rruleFreqType = rruleFreqType;
    }

    public int getAmountOfDurationMultipleToFreq() {
        return amountOfDurationMultipleToFreq;
    }

    public void setAmountOfDurationMultipleToFreq(int amountOfDurationMultipleToFreq) {
        this.amountOfDurationMultipleToFreq = amountOfDurationMultipleToFreq;
    }
}
