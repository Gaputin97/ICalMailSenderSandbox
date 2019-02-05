package by.iba.bussiness.meeting.service.v1;

import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.token.model.JavaWebToken;
import by.iba.bussiness.token.service.TokenService;
import by.iba.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class MeetingServiceImpl implements MeetingService {
    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);
    private TokenService tokenService;
    private String findMeetingByIdEndpoint;
    private RestTemplate restTemplate;

    @Autowired
    public MeetingServiceImpl(TokenService tokenService,
                              RestTemplate restTemplate,
                              @Value("${meeting_by_id_endpoint}") String findMeetingByIdEndpoint) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.findMeetingByIdEndpoint = findMeetingByIdEndpoint;
    }

    @Override
    public Meeting getMeetingById(HttpServletRequest request, String id) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        Meeting meeting;
        try {
            ResponseEntity<Meeting> meetingResponseEntity = restTemplate.exchange(findMeetingByIdEndpoint + id,
                    HttpMethod.GET, httpEntity, Meeting.class);
            meeting = meetingResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Cant find meeting by id: " + id, e);
            throw new ServiceException("Can't get meeting from third-party service with id " + id + ". " + e);
        }
        return meeting;
    }
}