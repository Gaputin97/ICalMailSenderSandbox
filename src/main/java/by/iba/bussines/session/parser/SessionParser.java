package by.iba.bussines.session.parser;

import by.iba.bussines.session.constants.SessionConstants;
import by.iba.bussines.session.model.Session;
import by.iba.bussines.timeslot.model.TimeSlot;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SessionParser {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(SessionConstants.DATE_FORMAT);

    public Session timeSlotToSession(TimeSlot timeSlot) {
        Date startDate;
        Date endDate;
        String stringStartDate = timeSlot.getStartDateTime();
        String stringEndDate = timeSlot.getEndDateTime();
        try {
            startDate = dateFormat.parse(stringStartDate);
            endDate = dateFormat.parse(stringEndDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Session(startDate, endDate);

    }

    public List<Session> timeSlotListToSessionList(List<TimeSlot> timeSlots) {
        List<Session> sessions = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots) {
            Date startDate;
            Date endDate;
            String stringStartDate = timeSlot.getStartDateTime();
            String stringEndDate = timeSlot.getEndDateTime();
            try {
                startDate = dateFormat.parse(stringStartDate);
                endDate = dateFormat.parse(stringEndDate);
                Session session = new Session(startDate, endDate);
                sessions.add(session);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return sessions;

    }
}
