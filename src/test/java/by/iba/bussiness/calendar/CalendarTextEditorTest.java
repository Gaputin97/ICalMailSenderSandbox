package by.iba.bussiness.calendar;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalendarTextEditorTest {
    private CalendarTextEditor calendarTextEditor = new CalendarTextEditor();

    @Test
    public void testCalendarTextEditor() {
        String inputString = "METHOD:REQUEST\r\n";

        String actualString = calendarTextEditor.replaceColonToEqual(inputString);

        assertEquals("Error caused by text editor","method=REQUEST\r\n", actualString);
    }
}