package by.iba.bussiness.meeting.service.v1;

import by.iba.bussiness.meeting.constants.MeetingConstants;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.token.model.JavaWebToken;
import by.iba.bussiness.token.service.TokenService;
import by.iba.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);

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
            ResponseEntity<Meeting> meetingResponseEntity = restTemplate.exchange(meetingConstants.getMeetingEndpointById(id),
                    HttpMethod.GET, httpEntity, Meeting.class);
            meeting = meetingResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Cant find meeting by id", e);
            throw new ServiceException("Can't find meeting with id " + id);
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
            logger.error("Can't get all meetings", e);
            throw new ServiceException("Can't get all meetings");
        }
        return meetings;
    }
}
