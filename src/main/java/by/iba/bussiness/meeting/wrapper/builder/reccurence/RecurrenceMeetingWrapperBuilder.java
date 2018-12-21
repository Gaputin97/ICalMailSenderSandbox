package by.iba.bussines.meeting.wrapper.builder.reccurence;

import by.iba.bussines.meeting.wrapper.builder.AbstractMeetingWrapperBuilder;
import by.iba.bussines.meeting.wrapper.model.reccurence.RecurrenceMeetingWrapper;
import by.iba.bussines.rrule.model.Rrule;
import org.springframework.stereotype.Component;

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
