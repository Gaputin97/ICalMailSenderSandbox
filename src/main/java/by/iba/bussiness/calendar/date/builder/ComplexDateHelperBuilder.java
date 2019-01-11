package by.iba.bussiness.calendar.date.builder;

import by.iba.bussiness.calendar.date.model.complex.ComplexDateHelper;
import by.iba.bussiness.calendar.session.Session;

import java.util.List;

public class ComplexDateHelperBuilder {
    private List<Session> sessionList;

    public ComplexDateHelperBuilder setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
        return this;
    }

    public ComplexDateHelper build() {
        ComplexDateHelper complexDateHelper = new ComplexDateHelper();
        complexDateHelper.setSessionList(sessionList);
        return complexDateHelper;
    }
}
