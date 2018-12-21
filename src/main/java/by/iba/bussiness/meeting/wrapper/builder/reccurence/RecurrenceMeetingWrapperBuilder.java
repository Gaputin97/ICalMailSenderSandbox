package by.iba.bussiness.meeting.wrapper.builder.reccurence;

import by.iba.bussiness.meeting.wrapper.model.reccurence.RecurrenceMeetingWrapper;
import by.iba.bussiness.rrule.model.Rrule;
import org.springframework.stereotype.Component;

@Component
public class RecurrenceMeetingWrapperBuilder{
    private Rrule rrule;

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
