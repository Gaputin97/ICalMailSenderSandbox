package by.iba.bussiness.calendar.date.helper.model.complex;

import by.iba.bussiness.calendar.date.helper.model.DateHelper;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.meeting.MeetingType;

import java.util.List;

public class ComplexDateHelper extends DateHelper {

    private List<Session> sessionList;

    public ComplexDateHelper() {
        super(MeetingType.COMPLEX);
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }
}
