package by.iba.bussines.enrollment.service.v1;

import by.iba.bussines.enrollment.constants.EnrollmentConstants;
import by.iba.bussines.enrollment.dao.EnrollmentRepository;
import by.iba.bussines.enrollment.model.Enrollment;
import by.iba.bussines.enrollment.service.EnrollmentService;
import by.iba.bussines.exception.ServiceException;
import by.iba.bussines.token.model.JavaWebToken;
import by.iba.bussines.token.service.TokenService;
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
public class EnrollmentServiceImpl implements EnrollmentService {
    private TokenService tokenService;
    private RestTemplate restTemplate;
    private EnrollmentConstants enrollmentConstants;
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentServiceImpl(TokenService tokenService,
                                 RestTemplate restTemplate,
                                 EnrollmentConstants enrollmentConstants,
                                 EnrollmentRepository enrollmentRepository) {
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.enrollmentConstants = enrollmentConstants;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Enrollment getEnrollmentByEmailAndMeeting(HttpServletRequest request, String meetingId, String email) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        Enrollment enrollment;
        try {
            ResponseEntity<Enrollment> enrollmentResponseEntity = restTemplate.exchange(enrollmentConstants.getEnrollmentByEmailAndMeetingId(meetingId, email),
                    HttpMethod.GET, httpEntity, Enrollment.class);
            enrollment = enrollmentResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceException(e.getMessage());
        }
        return enrollment;
    }
}
