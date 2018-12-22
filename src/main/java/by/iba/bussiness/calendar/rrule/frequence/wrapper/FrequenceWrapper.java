package by.iba.bussiness.calendar.rrule.frequence.wrapper;


import by.iba.bussiness.calendar.rrule.frequence.model.RruleFreqType;

public class FrequenceWrapper {

    private RruleFreqType rruleFreqType;
    private int amountOfDurationMultipleToFreq;

    public FrequenceWrapper(RruleFreqType rruleFreqType, int minimumDuration) {
        this.rruleFreqType = rruleFreqType;
        this.amountOfDurationMultipleToFreq = minimumDuration;
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
