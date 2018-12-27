package by.iba.bussiness.calendar.date.model.reccurence;

import by.iba.bussiness.meeting.MeetingType;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.rrule.Rrule;

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
