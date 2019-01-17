package by.iba.bussiness.meeting.timeslot;

import java.util.List;

public class TimeSlot{
    private int id;
    private long duration;
    private String endDateTime;
    private String facilityInformation;
    private List<String> resourceEmails;
    private String startDateTime;
    private String uuid;

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

    public String getFacilityInformation() {
        return facilityInformation;
    }

    public void setFacilityInformation(String facilityInformation) {
        this.facilityInformation = facilityInformation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getResourceEmails() {
        return resourceEmails;
    }

    public void setResourceEmails(List<String> resourceEmails) {
        this.resourceEmails = resourceEmails;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
