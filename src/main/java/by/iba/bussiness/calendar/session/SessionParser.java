package by.iba.bussiness.calendar.session;

import by.iba.bussiness.meeting.timeslot.TimeSlot;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SessionParser {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat(SessionConstants.DATE_FORMAT);

    public List<Session> timeSlotListToSessionList(List<TimeSlot> timeSlots) {
        List<Session> sessionList = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots) {
            String stringStartDate = timeSlot.getStartDateTime();
            String stringEndDate = timeSlot.getEndDateTime();
            int id;
            Date startDate;
            Date endDate;
            try {
                id = timeSlot.getId();
                startDate = dateFormat.parse(stringStartDate);
                endDate = dateFormat.parse(stringEndDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            sessionList.add(new Session(id, startDate, endDate));
        }
        return sessionList;
    }
}
