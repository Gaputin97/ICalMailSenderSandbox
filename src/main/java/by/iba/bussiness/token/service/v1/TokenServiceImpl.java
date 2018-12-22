package by.iba.bussiness.token.service.v1;

import by.iba.exception.ServiceException;
import by.iba.bussiness.token.constants.TokenConstants;
import by.iba.bussiness.token.model.JavaWebToken;
import by.iba.bussiness.token.service.TokenService;
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

@Service
public class TokenServiceImpl implements TokenService {
    private final static Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    private RestTemplate restTemplate;
    private TokenConstants tokenConstants;

    @Autowired
    public TokenServiceImpl(RestTemplate restTemplate, TokenConstants tokenConstants) {
        this.restTemplate = restTemplate;
        this.tokenConstants = tokenConstants;
    }

    @Override
    public JavaWebToken getJavaWebToken(HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        JavaWebToken javaWebToken;
        try {
            ResponseEntity<JavaWebToken> javaWebTokenResponseEntity = restTemplate.exchange(tokenConstants.getTokenEndpoint(), HttpMethod.GET, httpEntity, JavaWebToken.class);
            javaWebToken = javaWebTokenResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Error while try to get token: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        return javaWebToken;
    }
}
