package by.iba.bussiness.calendar.date.model.reccurence;

import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.rrule.model.Rrule;

public class RecurrenceDateHelper extends DateHelper {
    private Rrule rrule;

    public RecurrenceDateHelper() {
        super(MeetingType.RECURRENCE);
    }

    public Rrule getRrule() {
        return rrule;
    }

    public void setRrule(Rrule rrule) {
        this.rrule = rrule;
    }
}
