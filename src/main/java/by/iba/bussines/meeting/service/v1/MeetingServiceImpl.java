package by.iba.bussines.meeting.service.v1;

import by.iba.bussines.exception.ServiceException;
import by.iba.bussines.meeting.constants.MeetingConstants;
import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.service.MeetingService;
import by.iba.bussines.token.model.JavaWebToken;
import by.iba.bussines.token.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {
    private TokenService tokenService;
    private MeetingConstants meetingConstants;
    private RestTemplate restTemplate;

    @Autowired
    public MeetingServiceImpl(TokenService tokenService,
                              MeetingConstants meetingConstants,
                              RestTemplate restTemplate) {
        this.tokenService = tokenService;
        this.meetingConstants = meetingConstants;
        this.restTemplate = restTemplate;
    }

    @Override
    public Meeting getMeetingById(HttpServletRequest request, String id) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        Meeting meeting;
        try {
            ResponseEntity<Meeting> meetingResponseEntity = restTemplate.exchange(meetingConstants.getMeetingEndpointById(),
                    HttpMethod.GET, httpEntity, Meeting.class, id);
            meeting = meetingResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceException(e.getMessage());
        }
        return meeting;
    }

    @Override
    public List<Meeting> getAllMeetings(HttpServletRequest request) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        List<Meeting> meetings;
        try {
            ResponseEntity<Meeting[]> meetingResponseEntity = restTemplate.exchange(meetingConstants.getAllMeetingsEndpoint(),
                    HttpMethod.GET, httpEntity, Meeting[].class);
            meetings = Arrays.asList(meetingResponseEntity.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceException(e.getMessage());
        }
        return meetings;
    }
}
