package by.iba.bussiness.meeting.wrapper.model.single;

import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussiness.session.model.Session;

public class SingleMeetingWrapper extends MeetingWrapper {
    private Session session;

    public SingleMeetingWrapper() {
        super(MeetingType.SINGLE);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
