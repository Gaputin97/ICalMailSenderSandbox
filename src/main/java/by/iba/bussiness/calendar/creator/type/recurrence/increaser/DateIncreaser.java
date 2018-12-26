package by.iba.bussiness.calendar.creator.type.recurrence.increaser;

import by.iba.bussiness.calendar.rrule.frequence.model.RruleFreqType;
import by.iba.bussiness.calendar.session.constants.SessionConstants;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateIncreaser {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat(SessionConstants.DATE_FORMAT);

    public String increaseAndParse(RruleFreqType rruleFreqType, long interval, Date date) {
        long milliseconds = date.getTime();
        milliseconds += rruleFreqType.getMillisecondsInFreq() * interval;
        Date helpDate = new Date(milliseconds);
        return dateFormat.format(helpDate);

    }
}
