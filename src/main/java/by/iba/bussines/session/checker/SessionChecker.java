package by.iba.bussines.session.checker;

import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.session.model.Session;
import by.iba.bussines.session.parser.SessionParser;
import by.iba.bussines.timeslot.service.v1.TimeSlotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SessionChecker {

    private TimeSlotServiceImpl timeSlotService;
    private SessionParser sessionParser;

    @Autowired
    public SessionChecker(TimeSlotServiceImpl timeSlotService, SessionParser sessionParser) {
        this.timeSlotService = timeSlotService;
        this.sessionParser = sessionParser;
    }

    public boolean doAllSessionsTheSame(Meeting meeting) {
        List<Session> sessions = sessionParser.timeSlotListToSessionList(meeting.getTimeSlots());
        List<Long> sessionDurations = new ArrayList<>(sessions.size());
        sessions.forEach(x -> sessionDurations.add(x.getDuration()));
        return sessionDurations.stream().allMatch(sessionDurations.get(0)::equals);
    }
}
