package by.iba.bussiness.calendar.date.helper.model.single;

import by.iba.bussiness.calendar.date.helper.model.DateHelper;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.meeting.MeetingType;

public class SingleDateHelper extends DateHelper {
    private Session session;

    public SingleDateHelper() {
        super(MeetingType.SINGLE);
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
