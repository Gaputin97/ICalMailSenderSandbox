package by.iba.bussiness.calendar.attendee.model;

import javax.validation.constraints.Email;

public class Attendee {
    private String commonName;
    private @Email String email;

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
