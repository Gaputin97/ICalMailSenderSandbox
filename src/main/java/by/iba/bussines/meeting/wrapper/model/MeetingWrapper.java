package by.iba.bussines.meeting.wrapper.model;

import by.iba.bussines.calendar.factory.type.MeetingType;

import java.util.List;

public class MeetingWrapper {
    protected final MeetingType meetingType;
    public List<String> recipients;
    public String meetingId;

   public MeetingWrapper(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    public MeetingType getMeetingType() {
        return meetingType;
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
