package by.iba.bussiness.meeting.repository.v1;

import by.iba.bussiness.enrollment.repository.v1.EnrollmentRepositoryImpl;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.repository.MeetingRepository;
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
public class MeetingRepositoryImpl implements MeetingRepository {
    private final static Logger logger = LoggerFactory.getLogger(EnrollmentRepositoryImpl.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Meeting save(Meeting meeting) {
        try {
            mongoTemplate.save(meeting);
        } catch (Exception e) {
            logger.error("Error while trying to save meeting.", e);
            throw new RepositoryException("Error with database. Try again later");
        }
        return meeting;
    }

    @Override
    public Meeting getById(BigInteger id) {
        Query query = new Query(Criteria.where("id").is(id));
        Meeting meeting = mongoTemplate.findOne(query, Meeting.class);
        if (meeting == null) {
            logger.error("There are no meeting with id " + id);
        }
        return meeting;
    }
}
