package by.iba.bussiness.template;

import by.iba.bussiness.calendar.session.Session;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;


public class TemplateTimeSlotDefinerTest {

    private TemplateTimeSlotDefiner templateTimeSlotDefiner = new TemplateTimeSlotDefiner();
    private int highestId = 12;
    private int lowestId = 7;
    private int concreteId = 10;
    private Instant firstInstant = Instant.now();
    private Instant secondInstant = Instant.now();
    private Session sessionWithHighestId = new Session(highestId, firstInstant, secondInstant);
    private Session sessionWithLowestId = new Session(lowestId, firstInstant, secondInstant);
    private Session sessionWithConcreteId = new Session(concreteId, firstInstant, secondInstant);
    private List<Session> sessionList = Arrays.asList(sessionWithHighestId, sessionWithLowestId, sessionWithConcreteId);

    @Test
    public void testDefineHighestIdOfSessions() {
        int id = templateTimeSlotDefiner.defineHighestIdOfSessions(sessionList);

        Assert.assertEquals(12, id);
    }

    @Test
    public void testDefineLowestIdOfSessions() {
        int id = templateTimeSlotDefiner.defineLowestIdOfSessions(sessionList);

        Assert.assertEquals(7, id);
    }

    @Test
    public void testDefineSessionById() {
        Session session = templateTimeSlotDefiner.defineSessionById(concreteId, sessionList);

        Assert.assertEquals(session, sessionWithConcreteId);
    }
}