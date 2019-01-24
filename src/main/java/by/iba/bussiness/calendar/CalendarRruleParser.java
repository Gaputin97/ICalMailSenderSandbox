package by.iba.bussiness.calendar;

import by.iba.bussiness.calendar.rrule.Count;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.exception.CalendarException;
import net.fortuna.ical4j.model.Recur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
public class CalendarRruleParser {
    private static final Logger logger = LoggerFactory.getLogger(CalendarRruleParser.class);

    public Recur parseToCalendarRrule(Rrule rrule, String increasedUntilString) {
        Count count = rrule.getCount();
        long interval = rrule.getInterval();
        Frequency frequency = rrule.getFrequency();
        Recur recurrence;
        try {
            if (count.equals(Count.DEFAULT)) {
                recurrence = new Recur("FREQ=" + frequency.toString() + ";" + "INTERVAL="
                        + interval + ";" + "UNTIL=" + increasedUntilString + ";");
            } else {
                int intCount = count.getIntCount();
                recurrence = new Recur("FREQ=" + frequency.toString() + ";" + "COUNT="
                        + intCount + ";");
            }
        } catch (ParseException e) {
            logger.error("Cant parse rrule to calendar rrule", e);
            throw new CalendarException("Can't create calendar from your meeting", e);
        }
        return recurrence;
    }
}