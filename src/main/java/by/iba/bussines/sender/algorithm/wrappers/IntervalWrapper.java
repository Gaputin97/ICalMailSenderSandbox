package by.iba.bussines.sender.algorithm.wrappers;

public class IntervalWrapper {

    private boolean isDurationMultipleToFreq;
    private long minimumIntervalOfFreq;

    public IntervalWrapper(boolean isDurationMultipleToFreq, long minimumIntervalOfFreq) {
        this.isDurationMultipleToFreq = isDurationMultipleToFreq;
        this.minimumIntervalOfFreq = minimumIntervalOfFreq;
    }

    public long getMinimumIntervalOfFreq() {
        return minimumIntervalOfFreq;
    }

    public void setMinimumIntervalOfFreq(long minimumIntervalOfFreq) {
        this.minimumIntervalOfFreq = minimumIntervalOfFreq;
    }

    public boolean isDurationMultipleToFreq() {
        return isDurationMultipleToFreq;
    }

    public void setDurationMultipleToFreq(boolean durationMultipleToFreq) {
        isDurationMultipleToFreq = durationMultipleToFreq;
    }

}
