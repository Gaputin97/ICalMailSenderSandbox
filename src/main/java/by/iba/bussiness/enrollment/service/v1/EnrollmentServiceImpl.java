package by.iba.bussiness.enrollment.service.v1;

import by.iba.bussiness.enrollment.constants.EnrollmentConstants;
import by.iba.bussiness.enrollment.model.Enrollment;
import by.iba.bussiness.enrollment.repository.v1.EnrollmentRepositoryImpl;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.status.insert.InsertStatus;
import by.iba.bussiness.token.model.JavaWebToken;
import by.iba.bussiness.token.service.v1.TokenServiceImpl;
import by.iba.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final static Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    private TokenServiceImpl tokenService;
    private RestTemplate restTemplate;
    private EnrollmentConstants enrollmentConstants;
    private EnrollmentRepositoryImpl enrollmentRepository;

    @Autowired
    public EnrollmentServiceImpl(TokenServiceImpl tokenService,
                                 RestTemplate restTemplate,
                                 EnrollmentConstants enrollmentConstants,
                                 EnrollmentRepositoryImpl enrollmentRepository1) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.enrollmentConstants = enrollmentConstants;
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
            ResponseEntity<Enrollment> enrollmentResponseEntity = restTemplate.exchange(enrollmentConstants.getEnrollmentEndpointByEmailAndMeetingId(parentId, email),
                    HttpMethod.GET, httpEntity, Enrollment.class);
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
