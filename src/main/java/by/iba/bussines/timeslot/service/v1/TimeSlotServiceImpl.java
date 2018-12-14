package by.iba.bussines.timeslot.service.v1;

import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.service.v1.MeetingServiceImpl;;
import by.iba.bussines.timeslot.model.TimeSlot;
import by.iba.bussines.timeslot.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    private MeetingServiceImpl meetingService;

    @Autowired
    public TimeSlotServiceImpl(MeetingServiceImpl meetingService) {
        this.meetingService = meetingService;
    }

    @Override
    public List<TimeSlot> getMeetingTimeSlots(HttpServletRequest request, String meetingId) {
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        return meeting.getTimeSlots();
    }
}
