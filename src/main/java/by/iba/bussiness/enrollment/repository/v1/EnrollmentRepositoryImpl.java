package by.iba.bussiness.enrollment.repository.v1;

import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentRepositoryImpl.class);
    private final MongoTemplate mongoTemplate;

    @Autowired
    public EnrollmentRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Retryable(
            value = {RepositoryException.class},
            maxAttempts = 10,
            backoff = @Backoff(delay = 1000)
    )
    public Enrollment save(Enrollment enrollment) {
        try {
            mongoTemplate.save(enrollment);
        } catch (RuntimeException e) {
            logger.info("Can't save enrollment in database with enrollment id: " + enrollment.getId(), e);
            throw new RepositoryException("Can't locally save enrollment to database " + enrollment.toString());
        }
        return enrollment;
    }

    @Override
    public Enrollment getByEmailAndParentId(String userEmail, BigInteger parentId) {
        Query query = new Query(Criteria.where("parentId").is(parentId).and("userEmail").is(userEmail));
        Enrollment enrollment = mongoTemplate.findOne(query, Enrollment.class);
        if (enrollment == null) {
            logger.info("Can't locally find enrollment with parent ID " + parentId + " and user email " + userEmail);
        }
        return enrollment;
    }

    @Override
    public Enrollment getByEmailAndParentIdAndStatus(BigInteger parentId, String userEmail, String status) {
        Query query = new Query(Criteria.where("parentId").is(parentId).and("userEmail").is(userEmail).and("status").is(status));
        Enrollment enrollment = mongoTemplate.findOne(query, Enrollment.class);
        if (enrollment == null) {
            logger.info("Can't locally find enrollment with parent ID " + parentId + " and user email " + userEmail + " and enrollment status " + status);
        }
        return enrollment;
    }

    @Override
    public List<Enrollment> getAllByParentId(BigInteger parentId) {
        Query query = new Query(Criteria.where("parentId").is(parentId));
        List<Enrollment> enrollmentList = mongoTemplate.find(query, Enrollment.class);
        if (enrollmentList.isEmpty()) {
            logger.info("Can't locally find any enrollments with parent ID: " + parentId);
        }
        return enrollmentList;
    }
}
