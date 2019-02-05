package by.iba.bussiness.location.service.v1;

import by.iba.bussiness.location.Location;
import by.iba.bussiness.location.service.LocationService;
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
@PropertySource("endpoint.properties")
public class LocationServiceImpl implements LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);
    private TokenService tokenService;
    private RestTemplate restTemplate;
    private String locationByCodeEndpoint;

    @Autowired
    public LocationServiceImpl(TokenService tokenService,
                               RestTemplate restTemplate,
                               @Value("${location_by_code_endpoint}") String locationByCodeEndpoint) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.locationByCodeEndpoint = locationByCodeEndpoint;
    }

    @Override
    public Location getLocationByCode(HttpServletRequest request, String code) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        Location location;
        try {
            ResponseEntity<Location> locationResponseEntity = restTemplate.exchange(
                    locationByCodeEndpoint + code, HttpMethod.GET, httpEntity, Location.class);
            location = locationResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Can't exchange location by code: " + code, e);
            throw new ServiceException("Can't exchange invitation template by code");
        }
        return location;
    }
}
