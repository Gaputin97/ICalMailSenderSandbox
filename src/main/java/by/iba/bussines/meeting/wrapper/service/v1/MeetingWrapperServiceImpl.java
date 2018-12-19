package by.iba.bussines.meeting.wrapper.service.v1;

import by.iba.bussines.meeting.wrapper.model.AbstractMeetingWrapper;
import by.iba.bussines.meeting.wrapper.service.MeetingWrapperService;
import by.iba.bussines.session.service.v1.SessionServiceImpl;
import by.iba.bussines.timeslot.service.v1.TimeSlotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingWrapperServiceImpl implements MeetingWrapperService {
    private TimeSlotServiceImpl timeSlotService;
    private SessionServiceImpl sessionService;

    @Autowired
    public MeetingWrapperServiceImpl(TimeSlotServiceImpl timeSlotService, SessionServiceImpl sessionService) {
        this.timeSlotService = timeSlotService;
        this.sessionService = sessionService;
    }

    @Override
    public AbstractMeetingWrapper defineMeetingWrapper(String meetingId, List<String> recipients) {
        return null;


    }
}
