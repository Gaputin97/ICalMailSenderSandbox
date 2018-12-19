package by.iba.bussines.meeting.wrapper.model.single;

import by.iba.bussines.calendar.factory.type.MeetingType;
import by.iba.bussines.meeting.wrapper.model.AbstractMeetingWrapper;
import by.iba.bussines.session.model.Session;

public class SingleMeetingWrapper extends AbstractMeetingWrapper {
    private Session session;

    public SingleMeetingWrapper() {
        super(MeetingType.SIMPLE);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
