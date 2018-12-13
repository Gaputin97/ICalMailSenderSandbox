package by.iba.bussines.meeting.model;

import by.iba.bussines.owner.model.Owner;
import by.iba.bussines.timeslot.model.TimeSlot;

import java.util.ArrayList;
import java.util.List;

public class Meeting {
    private String description;
    private float duration;
    private String endDateTime;
    private String id;
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

    public String getDescription() {
        return description;
    }

    public float getDuration() {
        return duration;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public String getId() {
        return id;
    }

    public String getInvitationResourcesTemplate() {
        return invitationResourcesTemplate;
    }

    public String getInvitationTemplate() {
        return invitationTemplate;
    }

    public String getLocation() {
        return location;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public String getSummary() {
        return summary;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInvitationResourcesTemplate(String invitationResourcesTemplate) {
        this.invitationResourcesTemplate = invitationResourcesTemplate;
    }

    public void setInvitationTemplate(String invitationTemplate) {
        this.invitationTemplate = invitationTemplate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public void setOwner(Owner ownerObject) {
        this.owner = ownerObject;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
