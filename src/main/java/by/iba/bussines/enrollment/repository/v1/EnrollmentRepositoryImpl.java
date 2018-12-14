package by.iba.bussines.enrollment.dao.v1;

import by.iba.bussines.enrollment.dao.EnrollmentRepository;
import by.iba.bussines.enrollment.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Enrollment enrollment) {
        mongoTemplate.save(enrollment);
    }

    @Override
    public Enrollment getByEmailAbdMeetingId(String parentId, String userEmail) {
        Query query = new Query(Criteria.where("parentId").is(parentId).and("userEmail").is(userEmail));
        return mongoTemplate.findOne(query, Enrollment.class);
    }
}
