package by.iba.bussiness.calendar.session;

import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.exception.CalendarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SessionParser {
    private static final Logger logger = LoggerFactory.getLogger(SessionParser.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(SessionConstants.DATE_FORMAT);

    public List<Session> timeSlotListToSessionList(List<TimeSlot> timeSlots) {
        return timeSlots.stream().map(timeSlot -> {
            String stringStartDate = timeSlot.getStartDateTime();
            String stringEndDate = timeSlot.getEndDateTime();
            try {
                Instant startDate = dateFormat.parse(stringStartDate+"Z").toInstant();
                Instant endDate = dateFormat.parse(stringEndDate+"Z").toInstant();
                int id = timeSlot.getId();
                return new Session(id, startDate, endDate);
            } catch (ParseException ex) {
                logger.error("Can't parse time slot to date", ex);
                throw new CalendarException("Wrong time slot in meeting");
            }
        }).collect(Collectors.toList());
    }
}
