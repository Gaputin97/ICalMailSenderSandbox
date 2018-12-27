package by.iba.bussiness.calendar.rrule.frequence;

import by.iba.bussiness.calendar.rrule.frequence.model.Frequency;
import org.springframework.stereotype.Component;

@Component
public class FrequenceHelper {

    public boolean isDurationMultipleToFreq(Frequency frequency, long timeBetweenSessions) {
        boolean isDurationMultipleToFreq = false;
        long millisecondsInFreq = frequency.getMillisecondsInFreq();
        if (timeBetweenSessions % millisecondsInFreq == 0) {
            isDurationMultipleToFreq = true;
        }
        return isDurationMultipleToFreq;
    }


}
