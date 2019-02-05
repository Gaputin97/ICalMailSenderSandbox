package by.iba.bussiness.calendar;

import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.RruleCount;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import org.junit.Test;

public class CalendarRruleParserTest {
    private CalendarRruleParser calendarRruleParser = new CalendarRruleParser();

    @Test
    public void testParsingWhenRRuleCountEqualsZero() {
        Rrule rrule = new Rrule();
        rrule.setRruleCount(RruleCount.ZERO);
        rrule.setInterval(2);
        rrule.setFrequency(Frequency.DAILY);
    }

    @Test
    public void testParsingWhenRRuleCountNotEqualsZero() {

    }

    @Test
    public void testParsingWhenThrowsException() {

    }
}