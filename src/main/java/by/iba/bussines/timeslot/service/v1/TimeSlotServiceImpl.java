package by.iba.bussines.timeslot.service.v1;

import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.service.v1.MeetingServiceImpl;;
import by.iba.bussines.session.model.Session;
import by.iba.bussines.session.parser.SessionParser;
import by.iba.bussines.timeslot.model.TimeSlot;
import by.iba.bussines.timeslot.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    private MeetingServiceImpl meetingService;
    private SessionParser sessionParser;

    @Autowired
    public TimeSlotServiceImpl(MeetingServiceImpl meetingService, SessionParser sessionParser) {
        this.meetingService = meetingService;
        this.sessionParser = sessionParser;
    }
}
