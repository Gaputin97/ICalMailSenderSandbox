package by.iba.bussines.session.service.v1;

import by.iba.bussines.session.service.SessionService;
import by.iba.bussines.timeslot.model.TimeSlot;
import by.iba.bussines.timeslot.service.v1.TimeSlotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private TimeSlotServiceImpl timeSlotService;

    @Autowired
    public SessionServiceImpl(TimeSlotServiceImpl timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @Override
    public List<Date> getEventSessions(HttpServletRequest request, String meetingId) {
     List<TimeSlot> timeSlots = timeSlotService.getMeetingTimeSlots(request, meetingId);
     List<Date> sessions;
    }
}
