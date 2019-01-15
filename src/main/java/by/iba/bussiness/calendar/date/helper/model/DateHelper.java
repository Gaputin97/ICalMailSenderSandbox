package by.iba.bussiness.calendar.date.helper.model;

import by.iba.bussiness.meeting.MeetingType;

public class DateHelper {
    private final MeetingType meetingType;

    public DateHelper(MeetingType meetingType) {
        this.meetingType = meetingType;
    }

    public MeetingType getMeetingType() {
        return meetingType;
    }
}
