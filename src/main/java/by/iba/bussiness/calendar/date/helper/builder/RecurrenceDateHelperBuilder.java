package by.iba.bussiness.calendar.date.helper.builder;

import by.iba.bussiness.calendar.date.helper.model.reccurence.SimpleDateHelper;
import by.iba.bussiness.calendar.rrule.Rrule;


public class RecurrenceDateHelperBuilder {
    private Rrule rrule;

    public RecurrenceDateHelperBuilder setRrule(Rrule rrule) {
        this.rrule = rrule;
        return this;
    }

    public SimpleDateHelper build() {
        SimpleDateHelper recurrenceMeetingHelper = new SimpleDateHelper();
        recurrenceMeetingHelper.setRrule(rrule);
        return recurrenceMeetingHelper;
    }
}
