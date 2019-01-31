package by.iba.bussiness.calendar.creator.simple;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class ICalDateParserTest {
    private ICalDateParser iCalDateParser = new ICalDateParser();

    @Test
    public void testICalDateParser() {
        String sourceStringDate = Instant.now().toString();
        String expectedStringDate = sourceStringDate.replace("-", "").replace(":", "");

        String actualString = iCalDateParser.parseToICalDate(sourceStringDate);

        assertEquals(expectedStringDate, actualString);
    }
}