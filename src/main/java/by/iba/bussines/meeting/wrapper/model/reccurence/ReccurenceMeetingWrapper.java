package by.iba.bussines.meeting.wrapper.model.reccurence;

import by.iba.bussines.meeting.type.MeetingType;
import by.iba.bussines.meeting.wrapper.model.AbstractMeetingWrapper;
import by.iba.bussines.rrule.model.Rrule;

public class ReccurenceMeetingWrapper extends AbstractMeetingWrapper {

    private Rrule rrule;

    public ReccurenceMeetingWrapper() {
        super(MeetingType.RECCURENCE);
    }

    public Rrule getRrule() {
        return rrule;
    }

    public void setRrule(Rrule rrule) {
        this.rrule = rrule;
    }
}
