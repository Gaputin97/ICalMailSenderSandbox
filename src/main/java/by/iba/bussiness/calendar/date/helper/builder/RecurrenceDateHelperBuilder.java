package by.iba.bussiness.calendar.date.helper.builder;

import by.iba.bussiness.calendar.date.helper.model.reccurence.RecurrenceDateHelper;
import by.iba.bussiness.calendar.rrule.Rrule;


public class RecurrenceDateHelperBuilder {
    private Rrule rrule;

    public RecurrenceDateHelperBuilder setRrule(Rrule rrule) {
        this.rrule = rrule;
        return this;
    }

    public RecurrenceDateHelper build() {
        RecurrenceDateHelper recurrenceMeetingHelper = new RecurrenceDateHelper();
        recurrenceMeetingHelper.setRrule(rrule);
        return recurrenceMeetingHelper;
    }
}
