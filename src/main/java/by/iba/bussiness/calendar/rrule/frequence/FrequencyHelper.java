package by.iba.bussiness.calendar.rrule.frequence;

import org.springframework.stereotype.Component;

@Component
public class FrequencyHelper {
    public boolean isDurationMultipleToFreq(Frequency frequency, long timeBetweenSessions) {
        boolean isDurationMultipleToFrequency = false;
        long millisecondsInFrequency = frequency.getMillisecondsInFrequency();
        if (timeBetweenSessions % millisecondsInFrequency == 0) {
            isDurationMultipleToFrequency = true;
        }
        return isDurationMultipleToFrequency;
    }
}
