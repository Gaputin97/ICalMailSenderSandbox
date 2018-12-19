package by.iba.bussines.meeting.wrapper.model;

import by.iba.bussines.meeting.type.MeetingType;

import java.util.List;

public abstract class AbstractMeetingWrapper {

    public MeetingType meetingType;
    public List<String> recipients;
    public String meetingId;

    public AbstractMeetingWrapper(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }
}
