package by.iba.bussiness.enrollment.service.v1;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    @Override
    public Enrollment save(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment getByEmailAndParentId(String userEmail, BigInteger parentId) {
        return enrollmentRepository.getByEmailAndParentId(userEmail, parentId);
    }

    @Override
    public Enrollment getByEmailAndParentIdAndType(BigInteger parentId, String userEmail, String enrollmentStatus) {
        return enrollmentRepository.getByEmailAndParentIdAndStatus(parentId, userEmail, enrollmentStatus);
    }

    @Override
    public List<Enrollment> getAllByParentId(BigInteger parentId) {
        return enrollmentRepository.getAllByParentId(parentId);
    }
}