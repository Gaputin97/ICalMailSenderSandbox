package by.iba.bussiness.meeting.timeslot;

import java.util.List;
import java.util.Objects;

public class TimeSlot{
    private long duration;
    private String endDateTime;
    private String facilityInformation;
    private int id;
    private List<String> resourceEmails;
    private String startDateTime;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(endDateTime, timeSlot.endDateTime) &&
                Objects.equals(startDateTime, timeSlot.startDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endDateTime, startDateTime);
    }
}
