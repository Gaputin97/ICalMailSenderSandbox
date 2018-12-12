package by.iba.bussines.meeting.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:endpoint.properties")
@ConfigurationProperties
public class MeetingConstants {

    private String meetingByIdEndpoint;
    private String allMeetingsEndpoint;

    public String getMeetingByIdEndpoint() {
        return meetingByIdEndpoint;
    }

    public void setMeetingByIdEndpoint(String meetingByIdEndpoint) {
        this.meetingByIdEndpoint = meetingByIdEndpoint;
    }

    public String getAllMeetingsEndpoint() {
        return allMeetingsEndpoint;
    }

    public void setAllMeetingsEndpoint(String allMeetingsEndpoint) {
        this.allMeetingsEndpoint = allMeetingsEndpoint;
    }
}
