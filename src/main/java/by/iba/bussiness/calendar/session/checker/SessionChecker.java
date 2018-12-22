package by.iba.bussiness.calendar.session.checker;

import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.calendar.session.model.Session;
import by.iba.bussiness.calendar.session.parser.SessionParser;
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
