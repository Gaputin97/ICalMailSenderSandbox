package by.iba.bussiness.enrollment.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:endpoint.properties")
@ConfigurationProperties
public class EnrollmentConstants {
    private String enrollmentEndpointByEmailAndMeetingIdEndpoint;

    public String getEnrollmentEndpointByEmailAndMeetingId(String parentId, String email) {
        return enrollmentEndpointByEmailAndMeetingIdEndpoint + parentId + "/" + email;
    }

    public void setEnrollmentEndpointByEmailAndMeetingIdEndpoint(String enrollmentEndpointByEmailAndMeetingIdEndpoint) {
        this.enrollmentEndpointByEmailAndMeetingIdEndpoint = enrollmentEndpointByEmailAndMeetingIdEndpoint;
    }
}
