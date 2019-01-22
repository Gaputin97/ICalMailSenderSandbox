package by.iba.bussiness.template;

import by.iba.bussiness.calendar.session.Session;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class TemplateTimeSlotDefinerTest {


    @InjectMocks
    private TemplateTimeSlotDefiner templateTimeSlotDefiner;
    private static Session sessionWithHighestId;
    private static Session sessionWithLowestId;
    private static Session sessionWithConcreteId;
    private static int highestId;
    private static int lowestId;
    private static int concreteId;
    private static List<Session> sessionList;

    @BeforeClass
    public static void setUp() {
        highestId = 10;
        lowestId = 5;
        concreteId = 7;
        Instant firstInstant = null;
        Instant secondInstant = null;
        sessionWithHighestId = new Session(highestId, firstInstant, secondInstant);
        sessionWithLowestId = new Session(lowestId, firstInstant, secondInstant);
        sessionWithConcreteId = new Session(concreteId, firstInstant, secondInstant);
        sessionList = new ArrayList<>();
        sessionList.add(sessionWithConcreteId);
        sessionList.add(sessionWithHighestId);
        sessionList.add(sessionWithLowestId);
    }

    @Test
    public void defineHighestIdOfSessions() {
        int id = templateTimeSlotDefiner.defineHighestIdOfSessions(sessionList);
        Assert.assertEquals(id, highestId);
    }

    @Test
    public void defineLowestIdOfSessions() {
        int id = templateTimeSlotDefiner.defineLowestIdOfSessions(sessionList);
        Assert.assertEquals(id, lowestId);
    }

    @Test
    public void defineSessionById() {
        Session session = templateTimeSlotDefiner.defineSessionById(concreteId, sessionList);
        Assert.assertEquals(session, sessionWithConcreteId);
    }
}