package by.iba.bussiness.invitation_template.service.v1;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.invitation_template.service.InvitationTemplateService;
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
public class InvitationTemplateServiceImpl implements InvitationTemplateService {
    private static final Logger logger = LoggerFactory.getLogger(InvitationTemplateServiceImpl.class);
    private TokenService tokenService;
    private RestTemplate restTemplate;
    private String templateByCodeEndpoint;

    @Autowired
    public InvitationTemplateServiceImpl(TokenService tokenService,
                                         @Value("${template_by_code_endpoint}") String templateByCodeEndpoint,
                                         RestTemplate restTemplate) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.templateByCodeEndpoint = templateByCodeEndpoint;
    }

    @Override
    public InvitationTemplate getInvitationTemplateByCode(HttpServletRequest request, String code) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        InvitationTemplate invitationTemplate;
        try {
            ResponseEntity<InvitationTemplate> invitationTemplateResponseEntity = restTemplate.exchange(
                    templateByCodeEndpoint + code, HttpMethod.GET, httpEntity, InvitationTemplate.class);
            invitationTemplate = invitationTemplateResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Can't exchange invitation template by code: " + code, e);
            throw new ServiceException("Can't exchange invitation template by code");
        }
        return invitationTemplate;
    }
}
