package by.iba.bussiness.meeting.invitation.template.service.v1;

import by.iba.bussiness.meeting.invitation.template.service.InvitationTemplateService;
import by.iba.exception.ServiceException;
import by.iba.bussiness.meeting.invitation.template.constants.InvitationTemplateConstants;
import by.iba.bussiness.meeting.invitation.template.model.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.v1.MeetingServiceImpl;
import by.iba.bussiness.token.model.JavaWebToken;
import by.iba.bussiness.token.service.v1.TokenServiceImpl;
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

@Service
public class InvitationTemplateServiceImpl implements InvitationTemplateService {
    private TokenServiceImpl tokenService;
    private RestTemplate restTemplate;
    private InvitationTemplateConstants invitationTemplateConstants;
    private MeetingServiceImpl meetingService;

    @Autowired
    public InvitationTemplateServiceImpl(TokenServiceImpl tokenService,
                                         RestTemplate restTemplate,
                                         InvitationTemplateConstants invitationTemplateConstants,
                                         MeetingServiceImpl meetingService) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.invitationTemplateConstants = invitationTemplateConstants;
        this.meetingService = meetingService;
    }

    @Override
    public InvitationTemplate getInvitationTemplateById(HttpServletRequest request, String id) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        InvitationTemplate invitationTemplate;
        try {
            ResponseEntity<InvitationTemplate> invitationTemplateResponseEntity = restTemplate.exchange(invitationTemplateConstants.getTemplateById(),
                    HttpMethod.GET, httpEntity, InvitationTemplate.class, id);
            invitationTemplate = invitationTemplateResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceException(e.getMessage());
        }
        return invitationTemplate;
    }

    @Override
    public InvitationTemplate getInvitationTemplateByCode(HttpServletRequest request, String code) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        InvitationTemplate invitationTemplate;
        try {
            ResponseEntity<InvitationTemplate> invitationTemplateResponseEntity = restTemplate.exchange(invitationTemplateConstants.getTemplateByCode(),
                    HttpMethod.GET, httpEntity, InvitationTemplate.class, code);
            invitationTemplate = invitationTemplateResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceException(e.getMessage());
        }
        return invitationTemplate;
    }

    @Override
    public InvitationTemplate getInvitationTemplateByMeetingId(HttpServletRequest request, String MeetingId) {
        Meeting meeting = meetingService.getMeetingById(request, MeetingId);
        return getInvitationTemplateByCode(request, meeting.getInvitationResourcesTemplate());
    }
}
