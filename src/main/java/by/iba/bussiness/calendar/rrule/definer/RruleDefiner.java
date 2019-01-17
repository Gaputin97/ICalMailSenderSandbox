package by.iba.bussiness.calendar.rrule.definer;

import by.iba.bussiness.calendar.rrule.frequence.FrequencyDefiner;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.rrule.interval.IntervalDefiner;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class RruleDefiner {
    private static final Logger logger = LoggerFactory.getLogger(RruleDefiner.class);
    private FrequencyDefiner frequencyDefiner;
    private IntervalDefiner intervalDefiner;
    private ExDatesDefiner exDatesDefiner;

    @Autowired
    public RruleDefiner(FrequencyDefiner frequencyDefiner, IntervalDefiner intervalDefiner, ExDatesDefiner exDatesDefiner) {
        this.frequencyDefiner = frequencyDefiner;
        this.intervalDefiner = intervalDefiner;
        this.exDatesDefiner = exDatesDefiner;
    }

    public Rrule defineRrule(List<Session> sessions) {
        Collections.sort(sessions);
        Session lastSession = sessions.get(sessions.size() - 1);
        Session firstSession = sessions.get(0);

        Date startDateOfFirstSession = firstSession.getStartDateTime();
        Date startDateOfLastSession = lastSession.getEndDateTime();

        List<Date> startDatesOfSessions = new LinkedList();
        sessions.forEach(x -> startDatesOfSessions.add(x.getStartDateTime()));

        Frequency frequency = frequencyDefiner.defineFrequence(startDatesOfSessions);
        long interval;
        if (startDatesOfSessions.size() == 1) {
            interval = 1;
        } else {
            interval = intervalDefiner.defineInterval(startDatesOfSessions, frequency);
        }
        Rrule rrule = new Rrule();
        rrule.setInterval(interval);
        rrule.setFrequency(frequency);
        logger.info("Interval of rrule is " + interval + " and freq type is " + frequency.toString());

        exDatesDefiner.defineExDates(rrule, startDateOfFirstSession, startDateOfLastSession, startDatesOfSessions);
        logger.info("Amount of exdates is " + rrule.getExDates().size());
        return rrule;
    }
}
