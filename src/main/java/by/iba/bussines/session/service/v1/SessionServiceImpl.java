package by.iba.bussines.session.service.v1;

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
import java.util.Date;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private TimeSlotServiceImpl timeSlotService;
    private SessionParser sessionParser;
    private SessionSorter sessionSorter;

    @Autowired
    public SessionServiceImpl(TimeSlotServiceImpl timeSlotService, SessionParser sessionParser, SessionSorter sessionSorter) {
        this.timeSlotService = timeSlotService;
        this.sessionParser = sessionParser;
        this.sessionSorter = sessionSorter;
    }

    @Override
    public List<Session> getEventSessions(HttpServletRequest request, String meetingId) {
        List<TimeSlot> timeSlots = timeSlotService.getMeetingTimeSlots(request, meetingId);
        List<Session> sessions = new ArrayList<>(timeSlots.size());
        for (TimeSlot timeSlot : timeSlots) {
            Date startTimeSlotDate = sessionParser.stringToDate(timeSlot.getStartDateTime());
            Date endTimeSlotDate = sessionParser.stringToDate(timeSlot.getEndDateTime());
            Session session = new Session(startTimeSlotDate, endTimeSlotDate);
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
