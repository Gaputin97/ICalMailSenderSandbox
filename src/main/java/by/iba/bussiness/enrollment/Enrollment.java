package by.iba.bussiness.enrollment;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Document(collection = "enrollment")
public class Enrollment {
    @Id
    private BigInteger id;
    private BigInteger parentId;
    private String userEmail;
    private String userName;
    private String subject;
    private String calendarVersion;
    private String calendarStatus;
    private String calendarDate;
    private EnrollmentStatus enrollmentStatus;

    private enum EnrollmentStatus {
        ATTENDEED, CANCELLED;
    }

    public Enrollment() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
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

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
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
                ", enrollmentStatus=" + enrollmentStatus +
                '}';
    }
}
