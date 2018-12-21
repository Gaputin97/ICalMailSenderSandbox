package by.iba.bussiness.meeting.wrapper.builder.reccurence;

import by.iba.bussiness.meeting.wrapper.builder.AbstractMeetingWrapperBuilder;
import by.iba.bussiness.meeting.wrapper.model.reccurence.RecurrenceMeetingWrapper;
import by.iba.bussiness.rrule.model.Rrule;

public class RecurrenceMeetingWrapperBuilder extends AbstractMeetingWrapperBuilder<RecurrenceMeetingWrapperBuilder> {
    private Rrule rrule;

    public RecurrenceMeetingWrapperBuilder(RecurrenceMeetingWrapperBuilder builderClass) {
        super(builderClass);
    }

    public RecurrenceMeetingWrapperBuilder setRrule(Rrule rrule) {
        this.rrule = rrule;
        return this;
    }

    public RecurrenceMeetingWrapper build() {
        RecurrenceMeetingWrapper reccurenceMeetingWrapper = new RecurrenceMeetingWrapper();
        reccurenceMeetingWrapper.setRrule(rrule);
        return reccurenceMeetingWrapper;
    }
}
