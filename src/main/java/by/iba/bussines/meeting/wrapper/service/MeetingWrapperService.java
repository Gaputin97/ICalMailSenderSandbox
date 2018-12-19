package by.iba.bussines.meeting.wrapper.service;

import by.iba.bussines.meeting.wrapper.model.AbstractMeetingWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MeetingWrapperService {
    AbstractMeetingWrapper defineMeetingWrapper(HttpServletRequest request, String meetingId, List<String> recipients);
}
