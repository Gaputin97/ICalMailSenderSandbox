package by.iba.bussiness.meeting.wrapper.model.complex;

import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussiness.session.model.Session;

import java.util.List;

public class ComplexMeetingWrapper extends MeetingWrapper {
    private List<Session> session;

    public ComplexMeetingWrapper() {
        super(MeetingType.COMPLEX);
    }

    public List<Session> getSession() {
        return session;
    }

    public void setSessions(List<Session> session) {
        this.session = session;
    }
}
