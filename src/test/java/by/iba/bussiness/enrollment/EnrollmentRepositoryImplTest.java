package by.iba.bussiness.enrollment;

import by.iba.Runner;
import by.iba.bussiness.enrollment.repository.v1.EnrollmentRepositoryImpl;
import by.iba.bussiness.enrollment.status.EnrollmentStatus;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;
import java.util.List;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@ContextConfiguration(classes = Runner.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EnrollmentRepositoryImplTest {

    @Autowired
    private EnrollmentRepositoryImpl enrollmentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    private static final String USER_EMAIL = "shahrai.robert@gmail.com";
    private static final String SECOND_USER_EMAIL = "robert.shahrai@gmal.com";
    private static final BigInteger PARENT_ID = new BigInteger("1488");
    private static final String ENROLLMENT_STATUS = EnrollmentStatus.CONFIRMED.name();
    private static final BigInteger ENROLLMENT_ID = new BigInteger("1");

    @Before
    public void setUp() {
        mongoTemplate.dropCollection("enrollment");
    }

    @After
    public void tearDown() {
        mongoTemplate.dropCollection("enrollment");
    }

    @Test
    public void save() {
        //given
        Enrollment enrollment = new Enrollment();
        enrollment.setUserEmail(USER_EMAIL);
        enrollment.setId(ENROLLMENT_ID);
        //when
        Enrollment expectedEnrollment = enrollmentRepository.save(enrollment);
        //then
        Assert.assertEquals(expectedEnrollment.getUserEmail(), USER_EMAIL);
    }

    @Test
    public void saveOnExisted() {
        //given
        Enrollment enrollment = new Enrollment();
        enrollment.setUserEmail(USER_EMAIL);
        enrollment.setParentId(PARENT_ID);
        enrollment.setId(ENROLLMENT_ID);
        mongoTemplate.save(enrollment);
        //when
        enrollment.setUserEmail(SECOND_USER_EMAIL);
        Enrollment expectedEnrollment = enrollmentRepository.save(enrollment);
        //then
        Assert.assertEquals(expectedEnrollment.getUserEmail(), SECOND_USER_EMAIL);
        Assert.assertEquals(expectedEnrollment.getId(), ENROLLMENT_ID);
    }

    @Test
    public void getByEmailAndParentId() {
        //given
        Enrollment enrollment = new Enrollment();
        enrollment.setUserEmail(USER_EMAIL);
        enrollment.setParentId(PARENT_ID);
        mongoTemplate.save(enrollment);
        //when
        Enrollment expectedEnrollment = enrollmentRepository.getByEmailAndParentId(USER_EMAIL, PARENT_ID);
        //then
        Assert.assertEquals(expectedEnrollment.getUserEmail(), USER_EMAIL);
        Assert.assertEquals(expectedEnrollment.getParentId(), PARENT_ID);
    }

    @Test
    public void getByEmailAndParentIdAndType() {
        //given
        Enrollment enrollment = new Enrollment();
        enrollment.setUserEmail(USER_EMAIL);
        enrollment.setParentId(PARENT_ID);
        enrollment.setStatus(ENROLLMENT_STATUS);
        mongoTemplate.save(enrollment);
        //when
        Enrollment expectedEnrollment = enrollmentRepository.getByEmailAndParentIdAndStatus(PARENT_ID, USER_EMAIL, ENROLLMENT_STATUS);
        //then
        Assert.assertEquals(expectedEnrollment.getUserEmail(), USER_EMAIL);
        Assert.assertEquals(expectedEnrollment.getParentId(), PARENT_ID);
        Assert.assertEquals(expectedEnrollment.getStatus(), ENROLLMENT_STATUS);
    }

    @Test
    public void getAllByParentId() {
        //given
        Enrollment enrollment = new Enrollment();
        enrollment.setUserEmail(USER_EMAIL);
        enrollment.setParentId(PARENT_ID);
        Enrollment secondEnrollment = new Enrollment();
        secondEnrollment.setUserEmail(SECOND_USER_EMAIL);
        secondEnrollment.setParentId(PARENT_ID);
        mongoTemplate.save(enrollment);
        mongoTemplate.save(secondEnrollment);
        //when
        List<Enrollment> enrollmentList = enrollmentRepository.getAllByParentId(PARENT_ID);
        //then
        Assert.assertEquals(enrollmentList.size(), 2);
    }
}