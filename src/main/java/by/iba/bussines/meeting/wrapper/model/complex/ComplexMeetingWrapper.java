package by.iba.bussines.meeting.wrapper.model.complex;

import by.iba.bussines.calendar.factory.type.MeetingType;
import by.iba.bussines.meeting.wrapper.model.AbstractMeetingWrapper;
import by.iba.bussines.session.model.Session;

import java.util.List;

public class ComplexMeetingWrapper extends AbstractMeetingWrapper {
    private List<Session> session;

    public ComplexMeetingWrapper() {
        super(MeetingType.COMPLEX);
    }

    public List<Session> getSession() {
        return session;
    }

    public void setSession(List<Session> session) {
        this.session = session;
    }
}
