package by.iba.bussiness.enrollment.service.v1;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.token.model.JavaWebToken;
import by.iba.bussiness.token.service.TokenService;
import by.iba.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

@Service
@PropertySource("endpoint.properties")
public class EnrollmentServiceImpl implements EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    private TokenService tokenService;
    private RestTemplate restTemplate;

    @Value("${enrollment_by_email_and_meeting_id_endpoint}")
    private String ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID_AND_EMAIL;

    @Value("${enrollment_by_parent_id_endpoint}")
    private String ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID;

    @Autowired
    public EnrollmentServiceImpl(TokenService tokenService,
                                 RestTemplate restTemplate) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
    }

    @Override
    public Enrollment getEnrollmentByEmailAndParentId(HttpServletRequest request, BigInteger parentId, String email) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        Enrollment enrollment = null;
        try {
            ResponseEntity<Enrollment> enrollmentResponseEntity = restTemplate.exchange(
                    ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID_AND_EMAIL + parentId + "/" + email,
                    HttpMethod.GET,
                    httpEntity,
                    Enrollment.class);
            enrollment = enrollmentResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpStatus errorStatus = e.getStatusCode();
            if (errorStatus.equals(HttpStatus.NOT_FOUND) || errorStatus.equals(HttpStatus.UNAUTHORIZED) || errorStatus.equals(HttpStatus.FORBIDDEN)) {
                logger.error("Get enrollment error from ec3 service: " +  e.getStackTrace());
                throw new ServiceException(e.getMessage());
            }
        }
        return enrollment;
    }

    @Override
    public List<Enrollment> getEnrollmentByParentId(HttpServletRequest request, BigInteger parentId) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        List<Enrollment> enrollmentList = null;
        try {
            ResponseEntity<List<Enrollment>> resultEnrollmentList = restTemplate.exchange(
                    ENDPOINT_FIND_ENROLLMENT_BY_PARENT_ID + parentId,
                    HttpMethod.GET,
                    httpEntity,
                    new ParameterizedTypeReference<List<Enrollment>>(){});
            enrollmentList = resultEnrollmentList.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            HttpStatus errorStatus = e.getStatusCode();
            if (errorStatus.equals(HttpStatus.NOT_FOUND) || errorStatus.equals(HttpStatus.UNAUTHORIZED) || errorStatus.equals(HttpStatus.FORBIDDEN)) {
                logger.error("Get enrollment error from ec3 service: " + e.getStackTrace());
                throw new ServiceException(e.getMessage());
            }
        }
        return enrollmentList;
    }
}
