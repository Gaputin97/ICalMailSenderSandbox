package by.iba.bussines.enrollment.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:endpoint.properties")
@ConfigurationProperties
public class EnrollmentConstants {
    private String enrollmentByEmailAndMeetingId;

    public String getEnrollmentByEmailAndMeetingId(String meetingId, String email) {
        return enrollmentByEmailAndMeetingId + meetingId + "/" + email;
    }

    public void setEnrollmentByEmailAndMeetingId(String enrollmentByEmailAndMeetingId) {
        this.enrollmentByEmailAndMeetingId = enrollmentByEmailAndMeetingId;
    }
}
