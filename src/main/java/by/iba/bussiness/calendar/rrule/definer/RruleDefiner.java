package by.iba.bussiness.calendar.rrule.definer;

import by.iba.bussiness.calendar.rrule.RruleCount;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.rrule.frequence.FrequencyDefiner;
import by.iba.bussiness.calendar.rrule.interval.IntervalDefiner;
import by.iba.bussiness.calendar.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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
        Rrule rrule = new Rrule();
        if (sessions.size() == 1) {
            Frequency daily = Frequency.DAILY;
            rrule.setRruleCount(RruleCount.ONE_SESSION);
            rrule.setFrequency(daily);
        } else {
            List<Session> sortedSessions = new ArrayList<>(sessions);
            Collections.sort(sortedSessions);
            Session firstSession = sortedSessions.get(0);
            Session lastSession = sortedSessions.get(sessions.size() - 1);

            Instant startDateOfFirstSession = firstSession.getStartDateTime();
            Instant startDateOfLastSession = lastSession.getEndDateTime();

            List<Instant> startDatesOfSessions = new LinkedList();
            sortedSessions.forEach(x -> startDatesOfSessions.add(x.getStartDateTime()));

            Frequency frequency = frequencyDefiner.defineFrequency(startDatesOfSessions);
            long interval;
            if (startDatesOfSessions.size() == 1) {
                interval = 1;
            } else {
                interval = intervalDefiner.defineInterval(startDatesOfSessions, frequency);
            }
            rrule.setInterval(interval);
            rrule.setFrequency(frequency);
            logger.info("RRULE interval is " + interval + " and FREQUENCY type is " + frequency.toString());

            List<Instant> exDates = exDatesDefiner.defineExDates(rrule, startDateOfFirstSession, startDateOfLastSession, startDatesOfSessions);
            rrule.setExDates(exDates);
            rrule.setRruleCount(RruleCount.ZERO);
            logger.info("Amount of EXDATES is " + rrule.getExDates().size());

        }
        return rrule;
    }
}
