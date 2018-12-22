package by.iba.bussiness.calendar.rrule.definer;

import by.iba.bussiness.calendar.rrule.frequence.FrequenceDefiner;
import by.iba.bussiness.calendar.rrule.frequence.model.RruleFreqType;
import by.iba.bussiness.calendar.rrule.interval.IntervalDefiner;
import by.iba.bussiness.calendar.rrule.model.Rrule;
import by.iba.bussiness.calendar.session.model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RruleDefiner {
    private final static Logger logger = LoggerFactory.getLogger(RruleDefiner.class);
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
        Collections.sort(sessions);
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
        logger.info("Interval of rrule is " + interval + " and freq type is " + rruleFreqType.toString());

        exDatesDefiner.defineExDates(rrule, startDateOfFirstSession, startDateOfLastSession, startDatesOfSessions);
        logger.info("Amount of exdates is " + rrule.getExDates().size());
        return rrule;
    }

}
