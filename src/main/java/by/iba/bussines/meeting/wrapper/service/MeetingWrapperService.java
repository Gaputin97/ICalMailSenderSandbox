package by.iba.bussines.meeting.wrapper.service;

import by.iba.bussines.meeting.wrapper.model.AbstractMeetingWrapper;

import java.util.List;

public interface MeetingWrapperService {
    AbstractMeetingWrapper defineMeetingWrapper(String meetingId, List<String> recipients);
}
