package by.iba.bussiness.calendar.date.model.complex;

import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.session.model.Session;

import java.util.List;

public class ComplexDateHelper extends DateHelper {
    private List<Session> sessionList;

    public ComplexDateHelper() {
        super(MeetingType.COMPLEX);
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> session) {
        this.sessionList = session;
    }
}
