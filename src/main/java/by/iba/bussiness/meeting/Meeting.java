package by.iba.bussiness.meeting;

import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.bussiness.owner.Owner;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "meeting")
public class Meeting {
    @Id
    private ObjectId id;
    private String description;
    private short duration;
    private String startDateTime;
    private String endDateTime;
    private String invitationTemplateKey;
    private String location;
    private String locationInfo;
    private Owner owner;
    private String summary;
    private List<TimeSlot> timeSlots;
    private String timeZone;
    private String title;

    public Meeting() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getDuration() {
        return duration;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getInvitationTemplateKey() {
        return invitationTemplateKey;
    }

    public void setInvitationTemplateKey(String invitationTemplateKey) {
        this.invitationTemplateKey = invitationTemplateKey;
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

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
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
        return "Meeting{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", invitationTemplateKey='" + invitationTemplateKey + '\'' +
                ", location='" + location + '\'' +
                ", locationInfo='" + locationInfo + '\'' +
                ", owner=" + owner +
                ", summary='" + summary + '\'' +
                ", timeSlots=" + timeSlots +
                ", timeZone='" + timeZone + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
