package by.iba.bussiness.meeting.wrapper.model.reccurence;

import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussiness.rrule.model.Rrule;

public class RecurrenceMeetingWrapper extends MeetingWrapper {
    private Rrule rrule;

    public RecurrenceMeetingWrapper() {
        super(MeetingType.RECURRENCE);
    }

    public Rrule getRrule() {
        return rrule;
    }

    public void setRrule(Rrule rrule) {
        this.rrule = rrule;
    }
}
