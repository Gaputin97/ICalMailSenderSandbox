package by.iba.bussines.event.model;

import by.iba.bussines.owner.model.Owner;
import by.iba.bussines.timeslot.model.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String id;
    private String description;
    private float duration;
    private String endDateTime;
    private String chainId;
    private String invitationResourcesTemplate;
    private String invitationTemplate;
    private String location;
    private String locationInfo;
    private Owner owner;
    private String startDateTime;
    private String summary;
    private List<TimeSlot> timeSlots = new ArrayList();
    private String timeZone;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
