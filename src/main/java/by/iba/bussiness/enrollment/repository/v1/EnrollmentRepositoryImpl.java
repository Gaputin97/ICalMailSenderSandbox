package by.iba.bussiness.enrollment.repository.v1;

import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.Enrollment;

import by.iba.exception.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    private final static Logger logger = LoggerFactory.getLogger(EnrollmentRepositoryImpl.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Enrollment save(Enrollment enrollment) {
        try {
            mongoTemplate.save(enrollment);
        } catch (Exception e) {
            logger.error("Error while trying to save enrollment.", e);
            throw new RepositoryException("Error with database. Try again later");
        }
        return enrollment;
    }

    @Override
    public Enrollment getByEmailAndMeetingId(BigInteger parentId, String userEmail) {
        Query query = new Query(Criteria.where("parentId").is(parentId).and("userEmail").is(userEmail));
        Enrollment enrollment = mongoTemplate.findOne(query, Enrollment.class);
        if (enrollment == null) {
            logger.error("There are no enrollment with parentId " + parentId + " and user email " + userEmail);
        }
        return enrollment;
    }
}
