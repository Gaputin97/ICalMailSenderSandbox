package by.iba.bussiness.appointment;

import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.owner.Owner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Document(collection = "appointment")
public class Appointment {
    @Id
    private BigInteger id;
    private BigInteger meetingId;
    private int updateIndex;
    private int rescheduleIndex;
    private String description;
    private String location;
    private String locationInfo;
    private String subject;
    private String summary;
    private String title;
    private String startDateTime;
    private String endDateTime;
    private long duration;
    private Owner owner;
    private List<Session> sessionList;
    private String timeZone;

    public Appointment() {
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", updateIndex=" + updateIndex +
                ", rescheduleIndex=" + rescheduleIndex +
                ", subject='" + subject + '\'' +
                ", meetingId=" + meetingId +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", location='" + location + '\'' +
                ", locationInfo='" + locationInfo + '\'' +
                ", owner=" + owner +
                ", summary='" + summary + '\'' +
                ", sessionList=" + sessionList +
                ", timeZone='" + timeZone + '\'' +
                ", title='" + title + '\'' +
                '}';
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
                Objects.equals(owner, that.owner) &&
                Objects.equals(sessionList, that.sessionList) &&
                Objects.equals(timeZone, that.timeZone);

    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, description, location, locationInfo, summary, startDateTime, endDateTime, duration, owner, sessionList, timeZone);
    }
}
