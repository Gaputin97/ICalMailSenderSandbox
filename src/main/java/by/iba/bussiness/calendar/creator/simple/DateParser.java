package by.iba.bussiness.calendar.creator.simple;

import by.iba.bussiness.calendar.session.SessionConstants;
import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.exception.CalendarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

@Component
public class DateParser {
    private static final Logger logger = LoggerFactory.getLogger(SessionParser.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(SessionConstants.DATE_FORMAT);

    public Instant parseDate(String date) {
        try {
            return dateFormat.parse(date).toInstant();
        } catch (ParseException e) {
            logger.error("Can't parse string date to instant", e);
            throw new CalendarException("Error with dates");
        }
    }
}
