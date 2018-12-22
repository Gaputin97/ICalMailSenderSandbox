package by.iba.bussiness.calendar.date.builder.reccurence;

import by.iba.bussiness.calendar.date.model.reccurence.RecurrenceDateHelper;
import by.iba.bussiness.calendar.rrule.model.Rrule;
import org.springframework.stereotype.Component;

@Component
public class RecurrenceDateHelperBuilder {
    private Rrule rrule;

    public RecurrenceDateHelperBuilder setRrule(Rrule rrule) {
        this.rrule = rrule;
        return this;
    }

    public RecurrenceDateHelper build() {
        RecurrenceDateHelper reccurenceMeetingWrapper = new RecurrenceDateHelper();
        reccurenceMeetingWrapper.setRrule(rrule);
        return reccurenceMeetingWrapper;
    }
}
