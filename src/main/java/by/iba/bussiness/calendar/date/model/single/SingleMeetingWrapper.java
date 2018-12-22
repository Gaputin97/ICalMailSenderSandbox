package by.iba.bussiness.calendar.date.model.single;

import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.session.model.Session;

public class SingleMeetingWrapper extends DateHelper {
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
