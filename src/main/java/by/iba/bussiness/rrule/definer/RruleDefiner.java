package by.iba.bussines.rrule.definer;

import by.iba.bussines.rrule.frequence.FrequenceDefiner;
import by.iba.bussines.rrule.frequence.model.RruleFreqType;
import by.iba.bussines.rrule.interval.IntervalDefiner;
import by.iba.bussines.rrule.model.Rrule;
import by.iba.bussines.session.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RruleDefiner {
    private FrequenceDefiner frequenceDefiner;
    private IntervalDefiner intervalDefiner;
    private ExDatesDefiner exDatesDefiner;

    @Autowired
    public RruleDefiner(FrequenceDefiner frequenceDefiner, IntervalDefiner intervalDefiner, ExDatesDefiner exDatesDefiner) {
        this.frequenceDefiner = frequenceDefiner;
        this.intervalDefiner = intervalDefiner;
        this.exDatesDefiner = exDatesDefiner;
    }


    public Rrule defineRrule(List<Session> sessions) {
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

        exDatesDefiner.defineExDates(rrule, startDateOfFirstSession, startDateOfLastSession, startDatesOfSessions);
        return rrule;
    }

}
