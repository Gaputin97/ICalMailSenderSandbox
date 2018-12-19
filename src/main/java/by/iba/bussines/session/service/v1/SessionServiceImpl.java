package by.iba.bussines.session.service.v1;

import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.service.v1.MeetingServiceImpl;
import by.iba.bussines.session.model.Session;
import by.iba.bussines.session.parser.SessionParser;
import by.iba.bussines.session.service.SessionService;
import by.iba.bussines.session.sorter.SessionSorter;
import by.iba.bussines.timeslot.model.TimeSlot;
import by.iba.bussines.timeslot.service.v1.TimeSlotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private TimeSlotServiceImpl timeSlotService;
    private SessionParser sessionParser;
    private SessionSorter sessionSorter;
    private MeetingServiceImpl meetingService;

    @Autowired
    public SessionServiceImpl(TimeSlotServiceImpl timeSlotService, SessionParser sessionParser, SessionSorter sessionSorter, MeetingServiceImpl meetingService) {
        this.timeSlotService = timeSlotService;
        this.sessionParser = sessionParser;
        this.sessionSorter = sessionSorter;
        this.meetingService = meetingService;
    }

    @Override
    public List<Session> getEventSessions(HttpServletRequest request, String meetingId) {
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        List<TimeSlot> timeSlots = meeting.getTimeSlots();
        List<Session> sessions = new ArrayList<>(timeSlots.size());
        for (TimeSlot timeSlot : timeSlots) {
            Session session = sessionParser.timeSlotToSession(timeSlot);
            sessions.add(session);
        }
        return sessions;
    }

    @Override
    public List<Session> sortAndGetEventSessions(HttpServletRequest request, String meetingId) {
        List<Session> sessions = getEventSessions(request, meetingId);
        return sessionSorter.sortAndGetSessions(sessions);
    }


}
