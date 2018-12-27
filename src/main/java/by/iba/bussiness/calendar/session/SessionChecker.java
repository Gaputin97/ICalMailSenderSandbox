package by.iba.bussiness.calendar.session;

import by.iba.bussiness.meeting.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SessionChecker {

    private SessionParser sessionParser;

    @Autowired
    public SessionChecker(SessionParser sessionParser) {
        this.sessionParser = sessionParser;
    }

    public boolean doAllSessionsTheSame(Meeting meeting) {
        List<Session> sessions = sessionParser.timeSlotListToSessionList(meeting.getTimeSlots());
        List<Long> sessionDurations = new ArrayList<>(sessions.size());
        sessions.forEach(x -> sessionDurations.add(x.getDuration()));
        return sessionDurations.stream().allMatch(sessionDurations.get(0)::equals);
    }
}
