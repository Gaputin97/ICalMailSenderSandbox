package by.iba.bussines.rrule.definer;

import by.iba.bussines.rrule.frequence.FrequenceDefiner;
import by.iba.bussines.rrule.frequence.model.RruleFreqType;
import by.iba.bussines.rrule.interval.IntervalDefiner;
import by.iba.bussines.rrule.model.Rrule;
import by.iba.bussines.session.model.Session;

import java.util.*;

public class RruleDefiner {
    public Rrule defineIntervalAndFrequence(List<Session> sessions) {
        FrequenceDefiner frequenceDefiner = new FrequenceDefiner();
        IntervalDefiner intervalDefiner = new IntervalDefiner();
        Session lastSession = sessions.get(sessions.size() - 1);
        Session firstSession = sessions.get(0);

        Date startDateOfFirstSession = firstSession.getStartDate();
        Date startDateOfLastSession = lastSession.getEndDate();

        List<Date> startDatesOfSessions = new LinkedList();
        sessions.forEach(x -> startDatesOfSessions.add(x.getStartDate()));

        RruleFreqType rruleFreqType = frequenceDefiner.defineFrequence(startDatesOfSessions);
        long interval = intervalDefiner.defineInterval(startDatesOfSessions, rruleFreqType);
        Rrule rrule = new Rrule();
        rrule.setInterval(interval);
        rrule.setRruleFreqType(rruleFreqType);

        defineExDates(rrule, startDateOfFirstSession, startDateOfLastSession, startDatesOfSessions);
        return rrule;
    }

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
