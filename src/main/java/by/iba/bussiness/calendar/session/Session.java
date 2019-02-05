package by.iba.bussiness.calendar.session;

import by.iba.bussiness.calendar.rrule.constants.DateConstants;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class Session implements Comparable<Session> {
    private int id;
    private Instant startDateTime;
    private Instant endDateTime;
    private long duration;
    private long minuteDuration;

    public Session(int id, Instant startDateTime, Instant endDateTime) {
        this.id = id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        duration = endDateTime.toEpochMilli() - startDateTime.toEpochMilli();
        minuteDuration = duration/ DateConstants.MILLISECONDS_IN_MINUTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Instant getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Instant startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Instant getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Instant endDateTime) {
        this.endDateTime = endDateTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getMinuteDuration() {
        return minuteDuration;
    }

    public void setMinuteDuration(long minuteDuration) {
        this.minuteDuration = minuteDuration;
    }

    @Override
    public int compareTo(Session anotherSession) {
        long thisSessionStart = this.getStartDateTime().toEpochMilli();
        long anotherSessionStart = anotherSession.getStartDateTime().toEpochMilli();
        return Long.compare(thisSessionStart, anotherSessionStart);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return duration == session.duration &&
                Objects.equals(startDateTime, session.startDateTime) &&
                Objects.equals(endDateTime, session.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, endDateTime, duration);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.of("UTC"));
        return formatter.format(startDateTime) + " - " + formatter.format(endDateTime);
    }
}
