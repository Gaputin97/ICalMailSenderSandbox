package by.iba.bussiness.enrollment.repository.v1;

import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.model.Enrollment;

import by.iba.bussiness.enrollment.service.v1.EnrollmentServiceImpl;
import by.iba.exception.RepositoryException;

import by.iba.bussiness.status.insert.InsertStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    private final static Logger logger = LoggerFactory.getLogger(EnrollmentRepositoryImpl.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public InsertStatus save(Enrollment enrollment) {
        try {
            mongoTemplate.save(enrollment);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("Error with database. Try again later");
        }
        return new InsertStatus("Enrollment was added successfully");
    }

    @Override
    public Enrollment getByEmailAndMeetingId(String parentId, String userEmail) {
        Query query = new Query(Criteria.where("parentId").is(parentId).and("userEmail").is(userEmail));
        Enrollment enrollment = mongoTemplate.findOne(query, Enrollment.class);
        if (enrollment == null) {
            logger.error("There are no enrollment with parentId " + parentId + " and user email " + userEmail);
            throw new RepositoryException("Error with database. Try again later");
        }
        return enrollment;
    }
}
