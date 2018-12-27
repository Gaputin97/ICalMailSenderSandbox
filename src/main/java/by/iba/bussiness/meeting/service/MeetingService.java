package by.iba.bussiness.meeting.service;

import by.iba.bussiness.meeting.Meeting;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MeetingService {
    Meeting getMeetingById(HttpServletRequest request, String id);

    List<Meeting> getAllMeetings(HttpServletRequest request);
}
