package by.iba.bussiness.session.service.v1;

import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.service.v1.MeetingServiceImpl;
import by.iba.bussiness.session.model.Session;
import by.iba.bussiness.session.parser.SessionParser;
import by.iba.bussiness.session.service.SessionService;
import by.iba.bussiness.session.sorter.SessionSorter;
import by.iba.bussiness.timeslot.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private SessionParser sessionParser;
    private SessionSorter sessionSorter;
    private MeetingServiceImpl meetingService;

    @Autowired
    public SessionServiceImpl(SessionParser sessionParser, SessionSorter sessionSorter, MeetingServiceImpl meetingService) {
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
