package by.iba.bussiness.meeting.wrapper.builder;


public class AbstractMeetingWrapperBuilder<T extends AbstractMeetingWrapperBuilder> {
    protected final T builderClass;

    public AbstractMeetingWrapperBuilder(T builderClass) {
        this.builderClass = builderClass;
    }
}
