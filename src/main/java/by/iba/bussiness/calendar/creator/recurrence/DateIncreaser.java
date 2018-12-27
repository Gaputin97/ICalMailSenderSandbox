package by.iba.bussiness.calendar.creator.recurrence;

import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.session.SessionConstants;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateIncreaser {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(SessionConstants.DATE_FORMAT);

    public String increaseAndParse(Frequency frequency, long interval, Date date) {
        long milliseconds = date.getTime();
        milliseconds += frequency.getMillisecondsInFreq() * interval;
        Date helpDate = new Date(milliseconds);
        return dateFormat.format(helpDate);

    }
}
