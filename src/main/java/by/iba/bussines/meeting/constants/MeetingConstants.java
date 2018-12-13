package by.iba.bussines.meeting.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@PropertySource("classpath:endpoint.properties")
public class MeetingConstants {
    @Value("${meeting_by_id_endpoint}")
    private String meetingByIdEndpoint;

    @Value("${all_meetings_endpoint}")
    private String allMeetingsEndpoint;

    @Bean
    @Scope("prototype")
    public String getMeetingEndpointById(int meetingId) {
        return meetingByIdEndpoint + Integer.toString(meetingId);
    }

    @Bean
    public String getAllMeetingsEndpoint() {
        return allMeetingsEndpoint;
    }
}
