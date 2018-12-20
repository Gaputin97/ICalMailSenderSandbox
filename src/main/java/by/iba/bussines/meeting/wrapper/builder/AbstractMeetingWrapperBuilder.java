package by.iba.bussines.meeting.wrapper.builder;

public class AbstractMeetingWrapperBuilder<T> {
    protected final Class<T> builderClass;

    public AbstractMeetingWrapperBuilder(Class<T> builderClass) {
        this.builderClass = builderClass;
    }
}
