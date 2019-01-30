package by.iba.bussiness.calendar.creator.simple;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ICalDateParserTest {
    private ICalDateParser iCalDateParser = new ICalDateParser();

    @Test
    public void testICalDateParser() {
        String sourceString = "2019-01-29T12:20:26Z";
        String expectedString = "20190129T122026Z";

        String actualString = iCalDateParser.parseToICalDate(sourceString);

        assertEquals(expectedString, actualString);
    }
}