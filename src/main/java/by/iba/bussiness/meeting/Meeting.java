package by.iba.bussiness.meeting;

import by.iba.bussiness.contact.Contact;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.bussiness.owner.Owner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Document(collection = "meeting")
public class Meeting {
    @Id
    private BigInteger id;
    private String description;
    private long duration;
    private String startDateTime;
    private String endDateTime;
    private String invitationTemplate;
    private String location;
    private String locationInfo;
    private Owner owner;
    private String activityPasscode;
    private String plainDescription;
    private String summary;
    private List<TimeSlot> timeSlots;
    private String timeZone;
    private String title;
    private Contact contact;
    private String activityUrl;
    private String type;

    public Meeting() {
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

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getInvitationTemplate() {
        return invitationTemplate;
    }

    public void setInvitationTemplate(String invitationTemplate) {
        this.invitationTemplate = invitationTemplate;
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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getActivityPasscode() {
        return activityPasscode;
    }

    public void setActivityPasscode(String activityPasscode) {
        this.activityPasscode = activityPasscode;
    }

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", invitationTemplate='" + invitationTemplate + '\'' +
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
