package by.iba.bussiness.calendar.date.model.complex;

import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.session.model.Session;

import java.util.List;

public class ComplexDateHelper extends DateHelper {
    private List<Session> session;

    public ComplexDateHelper() {
        super(MeetingType.COMPLEX);
    }

    public List<Session> getSession() {
        return session;
    }

    public void setSessions(List<Session> session) {
        this.session = session;
    }
}
