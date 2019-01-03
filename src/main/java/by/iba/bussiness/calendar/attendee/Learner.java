package by.iba.bussiness.calendar.attendee;

import by.iba.bussiness.enrollment.EnrollmentType;

import javax.validation.constraints.Email;

public class Learner {
    private String commonName;
    @Email
    private String email;
    private EnrollmentType enrollmentType;

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnrollmentType getEnrollmentType() {
        return enrollmentType;
    }

    public void setEnrollmentType(EnrollmentType enrollmentType) {
        this.enrollmentType = enrollmentType;
    }
}
