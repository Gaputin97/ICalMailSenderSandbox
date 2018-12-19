package by.iba.bussines.meeting.wrapper.model;

import by.iba.bussines.calendar.factory.type.MeetingType;

import java.util.List;

public abstract class AbstractMeetingWrapper {
    public MeetingType meetingType;
    public List<String> recipients;
    public String meetingId;

    public AbstractMeetingWrapper(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

}
