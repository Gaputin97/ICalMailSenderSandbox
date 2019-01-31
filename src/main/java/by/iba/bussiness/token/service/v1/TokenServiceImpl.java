package by.iba.bussiness.token.service.v1;

import by.iba.bussiness.token.model.JavaWebToken;
import by.iba.bussiness.token.service.TokenService;
import by.iba.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;
    private String getTokenEndpoint;

    public TokenServiceImpl(RestTemplate restTemplate,
                            @Value("${token_endpoint}") String getTokenEndpoint) {
        this.restTemplate = restTemplate;
        this.getTokenEndpoint = getTokenEndpoint;
    }

    @Override
    public JavaWebToken getJavaWebToken(HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, request.getHeader(HttpHeaders.AUTHORIZATION));
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        JavaWebToken javaWebToken;
        try {
            ResponseEntity<JavaWebToken> javaWebTokenResponseEntity = restTemplate.exchange(getTokenEndpoint, HttpMethod.GET, httpEntity, JavaWebToken.class);
            javaWebToken = javaWebTokenResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Error while try to get token: ", e);
            throw new ServiceException("Error while trying to get token from third-party service" + e);
        }
        return javaWebToken;
    }
}
