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
    private BigInteger meetingId;
    private int updateIndex;
    private int rescheduleIndex;
    private String description;
    private String plainDescription;
    private String location;
    private String subject;
    private List<Session> sessionList;
    private String from;
    private String fromName;
    private String startDateTime;
    private String endDateTime;

    public Appointment() {
    }

    public String getPlainDescription() {
        return plainDescription;
    }

    public void setPlainDescription(String plainDescription) {
        this.plainDescription = plainDescription;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(meetingId, that.meetingId) &&
                Objects.equals(description, that.description) &&
                Objects.equals(location, that.location) &&
                Objects.equals(sessionList, that.sessionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingId, description, location, sessionList);
    }

}
