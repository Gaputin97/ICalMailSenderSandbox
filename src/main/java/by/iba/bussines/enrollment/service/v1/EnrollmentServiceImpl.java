package by.iba.bussines.enrollment.service.v1;

import by.iba.bussines.enrollment.constants.EnrollmentConstants;
import by.iba.bussines.enrollment.dao.v1.EnrollmentRepositoryImpl;
import by.iba.bussines.enrollment.model.Enrollment;
import by.iba.bussines.enrollment.service.EnrollmentService;
import by.iba.bussines.exception.ServiceException;
import by.iba.bussines.token.model.JavaWebToken;
import by.iba.bussines.token.service.TokenService;
import by.iba.bussines.token.service.v1.TokenServiceImpl;
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
    public Enrollment getEnrollmentByEmailAndMeeting(HttpServletRequest request, String parentId, String email) {
        JavaWebToken javaWebToken = tokenService.getJavaWebToken(request);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + javaWebToken.getJwt());
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        Enrollment enrollment;
        try {
            ResponseEntity<Enrollment> enrollmentResponseEntity = restTemplate.exchange(enrollmentConstants.getEnrollmentByEmailAndMeetingId(parentId, email),
                    HttpMethod.GET, httpEntity, Enrollment.class);
            enrollment = enrollmentResponseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ServiceException(e.getMessage());
        }
        return enrollment;
    }

    @Override
    public Enrollment getLocalEnrollmentByEmailAndMeeting(String parentId, String email) {
        return enrollmentRepository.getByEmailAbdMeetingId(parentId, email);
    }

    @Override
    public void saveEnrollment(Enrollment enrollment) {
        enrollmentRepository.save(enrollment);
    }
}
