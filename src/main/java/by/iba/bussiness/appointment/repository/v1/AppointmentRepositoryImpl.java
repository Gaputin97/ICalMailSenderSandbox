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
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


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
            logger.info("Can't save appointment into database: " + e.getStackTrace());
            throw new RepositoryException("Can't save appointment to database" + appointment.toString());
        }
        return appointment;
    }

    @Override
    public Appointment getByCode(String code) {
        Query query = new Query(Criteria.where("code").is(code));
        Appointment appointment = mongoTemplate.findOne(query, Appointment.class);
        if(appointment == null){
            logger.info("Can't get appointment from database by code " + code);
        }
        return appointment;
    }



    @Override
    public Appointment update(Appointment appointment) {
        Query query = new Query(Criteria.where("code").is(appointment.getInvitationTemplateKey()));
        Update update = new Update();
//        update.getUpdateObject()  //REWORK
//        Appointment appointment1 = mongoTemplate.update
        return null;
    }
}
