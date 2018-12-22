package by.iba.bussiness.calendar.date.model;

import by.iba.bussiness.meeting.type.MeetingType;

public class DateHelper {
    protected final MeetingType meetingType;

   public DateHelper(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }
}
