package by.iba.bussiness.enrollment.service.v1;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.repository.v1.EnrollmentRepositoryImpl;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.status.insert.InsertStatus;
import by.iba.bussiness.token.model.JavaWebToken;
import by.iba.bussiness.token.service.v1.TokenServiceImpl;
import by.iba.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

@Service
@PropertySource("endpoint.properties")
public class EnrollmentServiceImpl implements EnrollmentService {
    private final static Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    private TokenServiceImpl tokenService;
    private RestTemplate restTemplate;
    private EnrollmentRepositoryImpl enrollmentRepository;

    @Value("${enrollment_by_email_and_meeting_id_endpoint}")
    private String findEnrollmentByEmailAndMeetingIdEndpoint;

    @Autowired
    public EnrollmentServiceImpl(TokenServiceImpl tokenService,
                                 RestTemplate restTemplate,
                                 EnrollmentRepositoryImpl enrollmentRepository1) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.enrollmentRepository = enrollmentRepository1;
    }

    @Override
    public Enrollment getEnrollmentByEmailAndMeetingId(HttpServletRequest request, BigInteger parentId, String email) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        Enrollment enrollment = null;
        try {
            ResponseEntity<Enrollment> enrollmentResponseEntity = restTemplate.exchange(
                    findEnrollmentByEmailAndMeetingIdEndpoint + "/" + parentId + "/" + email,
                    HttpMethod.GET,
                    httpEntity,
                    Enrollment.class);
            enrollment = enrollmentResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Get enrollment error " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND || e.getStatusCode() == HttpStatus.UNAUTHORIZED || e.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new ServiceException(e.getMessage());
            }
        }
        return enrollment;
    }

    @Override
    public Enrollment getLocalEnrollmentByEmailAndMeetingId(BigInteger parentId, String email) {
        return enrollmentRepository.getByEmailAndMeetingId(parentId, email);
    }

    @Override
    public InsertStatus saveEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
}
