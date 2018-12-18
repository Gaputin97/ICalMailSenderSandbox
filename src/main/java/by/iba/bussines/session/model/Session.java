package by.iba.bussines.session.model;

import java.util.Date;

public class Session implements Comparable<Session> {

    private Date startDate;
    private Date endDate;

    public Session(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int compareTo(Session anotherSession) {
        long thisSessionStart = this.getStartDate().getTime();
        long anotherSessionStart = anotherSession.getStartDate().getTime();
        return thisSessionStart < anotherSessionStart ? -1 : thisSessionStart > anotherSessionStart ? 1 : 0;
    }
}
