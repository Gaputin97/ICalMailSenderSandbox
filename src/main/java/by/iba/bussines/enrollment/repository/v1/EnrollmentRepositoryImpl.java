package by.iba.bussines.enrollment.repository.v1;

import by.iba.bussines.enrollment.repository.EnrollmentRepository;
import by.iba.bussines.enrollment.model.Enrollment;

import by.iba.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public InsertStatus save(Enrollment enrollment) {
        try {
            mongoTemplate.save(enrollment);
        } catch (Exception e) {
            new RepositoryException(e.getMessage());
        }

        return new InsertStatus("Enrollment was added successfully");
    }

    @Override
    public Enrollment getByEmailAndMeetingId(String parentId, String userEmail) {
        Query query = new Query(Criteria.where("parentId").is(parentId).and("userEmail").is(userEmail));
        Enrollment enrollment = mongoTemplate.findOne(query, Enrollment.class);
        if (enrollment == null) {
            throw new RepositoryException("There are no enrollment with parentId " + parentId + " and user email " + userEmail);
        }
        return enrollment;
    }
}
