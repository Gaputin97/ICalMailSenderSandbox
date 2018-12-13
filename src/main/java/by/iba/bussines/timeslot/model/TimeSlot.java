package by.iba.bussines.timeslot.model;

import java.util.ArrayList;
import java.util.List;

public class TimeSlot {

    private float duration;

    private String endDateTime;

    private String facilityInformation;

    private float id;

    private List<String> resourceEmails = new ArrayList();

    private String startDateTime;

    public float getDuration() {
        return duration;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public String getFacilityInformation() {
        return facilityInformation;
    }

    public float getId() {
        return id;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public void setFacilityInformation(String facilityInformation) {
        this.facilityInformation = facilityInformation;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }
}
