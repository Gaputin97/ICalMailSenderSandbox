package by.iba.bussiness.appointment;

import by.iba.bussiness.calendar.session.Session;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Document(collection = "appointment")
public class Appointment {
    @Id
    private BigInteger id;
    private String title;
    private String from;
    private String fromName;
    private BigInteger meetingId;
    private int updateIndex;
    private int rescheduleIndex;
    private String description;
    private String plainDescription;
    private String location;
    private String locationInfo;
    private String subject;
    private String summary;
    private String startDateTime;
    private String endDateTime;
    private long duration;
    private List<Session> sessionList;
    private String timeZone;

    public Appointment() {
    }

    public String getPlainDescription() {
        return plainDescription;
    }

    public void setPlainDescription(String plainDescription) {
        this.plainDescription = plainDescription;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public int getUpdateIndex() {
        return updateIndex;
    }

    public void setUpdateIndex(int updateIndex) {
        this.updateIndex = updateIndex;
    }

    public int getRescheduleIndex() {
        return rescheduleIndex;
    }

    public void setRescheduleIndex(int rescheduleIndex) {
        this.rescheduleIndex = rescheduleIndex;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigInteger getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(BigInteger meetingId) {
        this.meetingId = meetingId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return duration == that.duration &&
                Objects.equals(meetingId, that.meetingId) &&
                Objects.equals(description, that.description) &&
                Objects.equals(location, that.location) &&
                Objects.equals(locationInfo, that.locationInfo) &&
                Objects.equals(summary, that.summary) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime) &&
                Objects.equals(from, that.from) &&
                Objects.equals(fromName, that.fromName) &&
                Objects.equals(sessionList, that.sessionList) &&
                Objects.equals(timeZone, that.timeZone);

    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, description, location, locationInfo, summary, startDateTime, endDateTime, duration, from, fromName, sessionList, timeZone);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", from='" + from + '\'' +
                ", fromName='" + fromName + '\'' +
                ", meetingId=" + meetingId +
                ", updateIndex=" + updateIndex +
                ", rescheduleIndex=" + rescheduleIndex +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", locationInfo='" + locationInfo + '\'' +
                ", subject='" + subject + '\'' +
                ", summary='" + summary + '\'' +
                ", startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", duration=" + duration +
                ", sessionList=" + sessionList +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
