package by.iba.bussiness.calendar.rrule.definer;

import by.iba.bussiness.calendar.rrule.Rrule;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ExDatesDefiner {
    public void defineExDates(Rrule rrule, Date startDateOfFirstSession, Date startDateOfLastSession, List<Date> startDatesOfSessions) {
        LinkedList<Date> linkedStartDatesOfSessions = new LinkedList<>(startDatesOfSessions);
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(startDateOfFirstSession);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(startDateOfLastSession);
        int frequency = rrule.getFrequency().getCalendarFrequency();
        int interval = rrule.getInterval().intValue();
        while (startCalendar.before(endCalendar)) {
            Date result = startCalendar.getTime();
            Date firstDate = linkedStartDatesOfSessions.getFirst();
            if (firstDate.equals(result)) {
                rrule.getExDates().add(result);
            } else {
                linkedStartDatesOfSessions.removeFirst();
            }
            startCalendar.add(frequency, interval);
        }
    }
}
