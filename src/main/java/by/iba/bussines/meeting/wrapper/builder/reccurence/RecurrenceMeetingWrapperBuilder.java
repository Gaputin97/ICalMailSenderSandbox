package by.iba.bussines.meeting.wrapper.builder.reccurence;

import by.iba.bussines.meeting.wrapper.builder.AbstractMeetingWrapperBuilder;
import by.iba.bussines.meeting.wrapper.model.reccurence.ReccurenceMeetingWrapper;
import by.iba.bussines.rrule.model.Rrule;

public class RecurrenceMeetingWrapperBuilder extends AbstractMeetingWrapperBuilder<RecurrenceMeetingWrapperBuilder> {

    private Rrule rrule;

    public RecurrenceMeetingWrapperBuilder(Class<RecurrenceMeetingWrapperBuilder> builderClass) {
        super(builderClass);
    }

    public RecurrenceMeetingWrapperBuilder setRrule(Rrule rrule) {
        this.rrule = rrule;
        return this;
    }

    public ReccurenceMeetingWrapper build() {
        ReccurenceMeetingWrapper reccurenceMeetingWrapper = new ReccurenceMeetingWrapper();
        reccurenceMeetingWrapper.setMeetingId(super.meetingId);
        reccurenceMeetingWrapper.setRecipients(super.recipients);
        reccurenceMeetingWrapper.setRrule(rrule);
        return reccurenceMeetingWrapper;
    }
}
