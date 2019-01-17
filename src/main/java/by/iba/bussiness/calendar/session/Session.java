package by.iba.bussiness.calendar.session;

import java.util.Date;

public class Session implements Comparable<Session> {
    private int id;
    private Date startDateTime;
    private Date endDateTime;
    private long duration;

    public Session(int id, Date startDateTime, Date endDateTime) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        duration = endDateTime.getTime() - startDateTime.getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public int compareTo(Session anotherSession) {
        long thisSessionStart = this.getStartDateTime().getTime();
        long anotherSessionStart = anotherSession.getStartDateTime().getTime();
        return Long.compare(thisSessionStart, anotherSessionStart);
    }

    @Override
    public String toString() {
        return startDateTime.toString() + " ---- " + endDateTime.toString();
    }
}
