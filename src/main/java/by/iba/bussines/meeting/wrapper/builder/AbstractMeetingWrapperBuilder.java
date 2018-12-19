package by.iba.bussines.meeting.wrapper.builder;

import by.iba.bussines.calendar.factory.type.MeetingType;

import java.util.List;

public class AbstractMeetingWrapperBuilder<T> {

    protected final Class<T> builderClass;
    protected String meetingId;
    protected MeetingType meetingType;
    protected List<String> recipients;

    public AbstractMeetingWrapperBuilder(Class<T> builderClass) {
        this.builderClass = builderClass;
    }

    public T setMeetingId(String meetingId) {
        this.meetingId = meetingId;
        return builderClass.cast(this);
    }

    public T setMeetingType(MeetingType meetingType) {
        this.meetingType = meetingType;
        return builderClass.cast(this);
    }

    public T setRecipients(List<String> recipients) {
        this.recipients = recipients;
        return builderClass.cast(this);
    }

}
