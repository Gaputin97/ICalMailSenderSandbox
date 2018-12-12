package by.iba.bussines.meeting.service;

import by.iba.bussines.meeting.model.Meeting;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MeetingService {

    Meeting getMeetingById(HttpServletRequest request, String id);

    List<Meeting> getAllMeetings(HttpServletRequest request);
}
