package by.iba.bussiness.calendar.rrule.definer;

import by.iba.bussiness.calendar.rrule.Rrule;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class ExDatesDefiner {
    public List<Instant> defineExDates(Rrule rrule, Instant startDateOfFirstSession, Instant startDateOfLastSession, List<Instant> startDatesOfSessions) {
        LinkedList<Instant> linkedStartDatesOfSessions = new LinkedList<>(startDatesOfSessions);
        List<Instant> exDates = new ArrayList<>();
        long milisOfFrequency = rrule.getFrequency().getMillisecondsInFreq();
        int interval = rrule.getInterval().intValue();
        if (!linkedStartDatesOfSessions.isEmpty()) {
            while (startDateOfFirstSession.isBefore(startDateOfLastSession)) {
                Instant firstStartDate = linkedStartDatesOfSessions.getFirst();
                if (firstStartDate.equals(startDateOfFirstSession)) {
                    exDates.add(startDateOfFirstSession);
                } else {
                    linkedStartDatesOfSessions.removeFirst();
                }
                startDateOfFirstSession = startDateOfFirstSession.plusMillis(milisOfFrequency * interval);
            }
        }
        return exDates;
    }
}
