package by.iba.bussines.calendar.factory.model;

import javax.validation.constraints.Email;

public class AttendeeModel {
    private String commonName;
    @Email
    private String email;

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
}
