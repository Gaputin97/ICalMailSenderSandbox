package by.iba.bussiness.appointment.repository.v1;

import by.iba.bussiness.appointment.Appointment;
import by.iba.bussiness.appointment.repository.AppointmentRepository;
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
public class AppointmentRepositoryImpl implements AppointmentRepository {
    private static final Logger logger = LoggerFactory.getLogger(AppointmentRepositoryImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Appointment save(Appointment appointment) {
        try {
            mongoTemplate.save(appointment);
        } catch (RuntimeException e) {
            logger.info("Can't save appointment into database: ", e);
            throw new RepositoryException("Can't save appointment");
        }
        return appointment;
    }

    @Override
    public Appointment getByMeetingId(BigInteger meetingId) {
        Query query = new Query(Criteria.where("meetingId").is(meetingId));
        Appointment appointment = mongoTemplate.findOne(query, Appointment.class);
        if (appointment == null) {
            logger.info("Can't get appointment from database by meeting ID " + meetingId);
        }
        return appointment;
    }
}
