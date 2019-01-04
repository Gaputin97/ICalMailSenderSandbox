package by.iba.bussiness.appointment;

import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.bussiness.owner.Owner;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Document(collection = "appointment")
public class Appointment {
    @Id
    private ObjectId id;
    private BigInteger meetingId;
    private String invitationTemplateKey;
    private int updateIndex;
    private int rescheduleIndex;
    private String blendedDescription;
    private String onlineDescription;
    private String faceToFaceDescription;
    private String description;
    private String locationBLD;
    private String locationLVC;
    private String locationILT;
    private String location;
    private String locationInfo;
    private String subject;
    private String summary;
    private String title;
    private String startDateTime;
    private String endDateTime;
    private float duration;
    private Owner owner;
    private List<TimeSlot> timeSlots;
    private String timeZone;

    public Appointment() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public String getBlendedDescription() {
        return blendedDescription;
    }

    public void setBlendedDescription(String blendedDescription) {
        this.blendedDescription = blendedDescription;
    }

    public String getFaceToFaceDescription() {
        return faceToFaceDescription;
    }

    public void setFaceToFaceDescription(String faceToFaceDescription) {
        this.faceToFaceDescription = faceToFaceDescription;
    }

    public String getOnlineDescription() {
        return onlineDescription;
    }

    public void setOnlineDescription(String onlineDescription) {
        this.onlineDescription = onlineDescription;
    }

    public String getLocationBLD() {
        return locationBLD;
    }

    public void setLocationBLD(String locationBLD) {
        this.locationBLD = locationBLD;
    }

    public String getLocationILT() {
        return locationILT;
    }

    public void setLocationILT(String locationILT) {
        this.locationILT = locationILT;
    }

    public String getLocationLVC() {
        return locationLVC;
    }

    public void setLocationLVC(String locationLVC) {
        this.locationLVC = locationLVC;
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
        return "Appointment{" +
                "id=" + id +
                ", updateIndex=" + updateIndex +
                ", rescheduleIndex=" + rescheduleIndex +
                ", blendedDescription='" + blendedDescription + '\'' +
                ", faceToFaceDescription='" + faceToFaceDescription + '\'' +
                ", onlineDescription='" + onlineDescription + '\'' +
                ", locationBLD='" + locationBLD + '\'' +
                ", locationILT='" + locationILT + '\'' +
                ", locationLVC='" + locationLVC + '\'' +
                ", subject='" + subject + '\'' +
                ", meetingId=" + meetingId +
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
