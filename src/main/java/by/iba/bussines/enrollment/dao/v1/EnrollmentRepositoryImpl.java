package by.iba.bussines.enrollment.dao.v1;

import by.iba.bussines.enrollment.dao.EnrollmentRepository;
import by.iba.bussines.enrollment.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    private String COLLECTION = "enrollment";

    @Override
    public void save(Enrollment enrollment) {
        mongoTemplate.save(enrollment, COLLECTION);
    }

    @Override
    public Enrollment getByEmailAbdMeetingId(String meetingId, String email) {
        Query query = new Query(Criteria.where("meetingId").is(meetingId).and("email").is(email));
        return mongoTemplate.findOne(query, Enrollment.class);
    }
}
