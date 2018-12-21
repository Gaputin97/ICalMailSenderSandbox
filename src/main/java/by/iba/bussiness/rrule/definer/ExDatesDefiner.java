package by.iba.bussiness.rrule.definer;

import by.iba.bussiness.rrule.model.Rrule;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ExDatesDefiner {
    public void defineExDates(Rrule rrule, Date startDateOfFirstSession, Date startDateOfLastSession, List<Date> startDatesOfSessions) {
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(startDateOfFirstSession);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(startDateOfLastSession);

        while (startCalendar.before(endCalendar)) {
            Date result = startCalendar.getTime();
            if (!((LinkedList<Date>) startDatesOfSessions).getFirst().equals(result)) {
                rrule.getExDates().add(result);
            } else {
                ((LinkedList<Date>) startDatesOfSessions).removeFirst();
            }
            startCalendar.add(rrule.getRruleFreqType().getFrequence(), rrule.getInterval().intValue());
        }
    }
}
