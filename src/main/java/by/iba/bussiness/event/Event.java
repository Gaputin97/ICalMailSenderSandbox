package by.iba.bussiness.event;

import by.iba.bussiness.owner.Owner;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "event")
public class Event {
    @Id
    private BigInteger id;
    @Field
    private String description;
    @Field
    private float duration;
    @Field
    private String startDateTime;
    @Field
    private String endDateTime;
    @Field
    private String chainId;
    @Field
    private String invitationResourcesTemplate;
    @Field
    private String invitationTemplate;
    @Field
    private String location;
    @Field
    private String locationInfo;
    @Field
    private Owner owner;
    @Field
    private String summary;
    @Field
    private List<TimeSlot> timeSlots = new ArrayList();
    @Field
    private String timeZone;
    @Field
    private String title;

    public Event() {
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

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
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

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public String getInvitationResourcesTemplate() {
        return invitationResourcesTemplate;
    }

    public void setInvitationResourcesTemplate(String invitationResourcesTemplate) {
        this.invitationResourcesTemplate = invitationResourcesTemplate;
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
}
