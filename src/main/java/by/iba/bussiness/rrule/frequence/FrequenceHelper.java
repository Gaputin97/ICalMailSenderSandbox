package by.iba.bussiness.rrule.frequence;

import by.iba.bussiness.rrule.frequence.model.RruleFreqType;
import org.springframework.stereotype.Component;

@Component
public class FrequenceHelper {

    public boolean isDurationMultipleToFreq(RruleFreqType rruleFreqType, long timeBetweenSessions) {
        boolean isDurationMultipleToFreq = false;
        long millisecondsInFreq = rruleFreqType.getMillisecondsInFreq();
        if (timeBetweenSessions % millisecondsInFreq == 0) {
            isDurationMultipleToFreq = true;
        }
        return isDurationMultipleToFreq;
    }


}
