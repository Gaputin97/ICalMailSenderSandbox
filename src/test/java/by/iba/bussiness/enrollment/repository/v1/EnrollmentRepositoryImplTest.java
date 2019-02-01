package by.iba.bussiness.enrollment.repository.v1;

import by.iba.Runner;
import by.iba.bussiness.enrollment.Enrollment;
import com.mongodb.Mongo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = Runner.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EnrollmentRepositoryImplTest {


    @Autowired
    private EnrollmentRepositoryImpl enrollmentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

//    @Before
//    public void setUp() {
//        mongoTemplate.dropCollection("enrollment");
//    }
//
//    @After
//    public void tearDown() {
//        mongoTemplate.dropCollection("enrollment");
//    }

    @Test
    public void save() {
        //given
        String userEmail = "userEmail322";
        Enrollment enrollment = new Enrollment();
        enrollment.setUserEmail(userEmail);
        //when
        Enrollment expectedEnrollment = enrollmentRepository.save(enrollment);
        //then
        Assert.assertEquals(expectedEnrollment.getUserEmail(), userEmail);
    }

    @Test
    public void getByEmailAndParentId() {
    }

    @Test
    public void getByEmailAndParentIdAndType() {
    }

    @Test
    public void getAllByParentId() {
    }
}