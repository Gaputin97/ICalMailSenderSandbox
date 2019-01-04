package by.iba.bussiness.enrollment;

import by.iba.bussiness.owner.Owner;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "enrollment")
public class Enrollment {
    @Id
    private ObjectId id;
    private BigInteger parentId;
    private String userEmail;
    private String userName;
    private String subject;
    private String calendarVersion;
    private String calendarStatus;
    private String calendarDate;
    private EnrollmentType enrollmentType;
    private String currentCalendarUid;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public BigInteger getParentId() {
        return parentId;
    }

    public void setParentId(BigInteger parentId) {
        this.parentId = parentId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCalendarVersion() {
        return calendarVersion;
    }

    public void setCalendarVersion(String calendarVersion) {
        this.calendarVersion = calendarVersion;
    }

    public String getCalendarStatus() {
        return calendarStatus;
    }

    public void setCalendarStatus(String calendarStatus) {
        this.calendarStatus = calendarStatus;
    }

    public String getCalendarDate() {
        return calendarDate;
    }

    public void setCalendarDate(String calendarDate) {
        this.calendarDate = calendarDate;
    }

    public EnrollmentType getEnrollmentType() {
        return enrollmentType;
    }

    public void setEnrollmentType(EnrollmentType enrollmentType) {
        this.enrollmentType = enrollmentType;
    }

    public String getCurrentCalendarUid() {
        return currentCalendarUid;
    }

    public void setCurrentCalendarUid(String currentCalendarUid) {
        this.currentCalendarUid = currentCalendarUid;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", userEmail='" + userEmail + '\'' +
                ", userName='" + userName + '\'' +
                ", subject='" + subject + '\'' +
                ", calendarVersion='" + calendarVersion + '\'' +
                ", calendarStatus='" + calendarStatus + '\'' +
                ", calendarDate='" + calendarDate + '\'' +
                ", enrollmentStatus=" + enrollmentType +
                '}';
    }
}
