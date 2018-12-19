package by.iba.bussines.meeting.wrapper.builder;

import by.iba.bussines.calendar.factory.type.MeetingType;

public class AbstractMeetingWrapperBuilder<T> {

    protected final Class<T> builderClass;

    public AbstractMeetingWrapperBuilder(Class<T> builderClass) {
        this.builderClass = builderClass;
    }
}
