package by.iba.bussiness.calendar.session;

import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SessionCheckerTest {
    private SessionChecker sessionChecker = new SessionChecker();

    @Test
    public void testWhenAllSessionsAreSame() {
        Instant startDateTime = Instant.now();
        Instant endDateTime = Instant.now();
        List<Session> sessions = Arrays.asList(
                new Session(1,
                        startDateTime.plus(1, ChronoUnit.HOURS),
                        endDateTime.plus(2, ChronoUnit.HOURS)
                ),
                new Session(2,
                        startDateTime.plus(3, ChronoUnit.HOURS),
                        endDateTime.plus(4, ChronoUnit.HOURS)
                ),
                new Session(3,
                        startDateTime.plus(5, ChronoUnit.HOURS),
                        endDateTime.plus(6, ChronoUnit.HOURS)
                ),
                new Session(4,
                        startDateTime.plus(7, ChronoUnit.HOURS),
                        endDateTime.plus(8, ChronoUnit.HOURS)
                )
        );

        boolean actualSameOfDuration = sessionChecker.isAllSessionsTheSame(sessions);
        assertTrue(actualSameOfDuration);
    }

    @Test
    public void testWhenAllSessionsAreNotSame() {
        Instant startDateTime = Instant.now();
        Instant endDateTime = Instant.now();
        List<Session> sessions = Arrays.asList(
                new Session(1,
                        startDateTime.plus(1, ChronoUnit.HOURS),
                        endDateTime.plus(3, ChronoUnit.HOURS)
                ),
                new Session(2,
                        startDateTime.plus(3, ChronoUnit.HOURS),
                        endDateTime.plus(10, ChronoUnit.HOURS)
                ),
                new Session(3,
                        startDateTime.plus(5, ChronoUnit.HOURS),
                        endDateTime.plus(8, ChronoUnit.HOURS)
                ),
                new Session(4,
                        startDateTime.plus(7, ChronoUnit.HOURS),
                        endDateTime.plus(8, ChronoUnit.HOURS)
                )
        );

        boolean actualSameOfDuration = sessionChecker.isAllSessionsTheSame(sessions);
        assertFalse(actualSameOfDuration);
    }
}