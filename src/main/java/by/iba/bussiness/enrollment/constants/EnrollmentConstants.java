package by.iba.bussiness.enrollment.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
@PropertySource("classpath:endpoint.properties")
@ConfigurationProperties
public class EnrollmentConstants {
    private String enrollmentByEmailAndMeetingIdEndpoint;

    public String getEnrollmentEndpointByEmailAndMeetingId(BigInteger parentId, String email) {
        return enrollmentByEmailAndMeetingIdEndpoint + parentId + "/" + email;
    }

    public void setEnrollmentByEmailAndMeetingIdEndpoint(String enrollmentByEmailAndMeetingIdEndpoint) {
        this.enrollmentByEmailAndMeetingIdEndpoint = enrollmentByEmailAndMeetingIdEndpoint;
    }


}
