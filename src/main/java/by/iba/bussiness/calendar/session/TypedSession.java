package by.iba.bussiness.calendar.session;

import java.time.Instant;

public class TypedSession extends Session {

    private SessionType sessionType;

    public TypedSession(int id, Instant startDateTime, Instant endDateTime) {
        super(id, startDateTime, endDateTime);
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }
}
