package by.iba.bussiness.calendar.creator.simple;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleMetingCalendarTemplateCreatorTest {
    private static final String PRODUCT_IDENTIFIER = "-//Your Learning//EN";
    @Mock
    private Calendar requestCalendar;
    @Mock
    private Calendar cancelCalendar;

    @InjectMocks
    private SimpleMetingCalendarTemplateCreator creator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(requestCalendar.getComponents()).thenReturn(new ComponentList<>());
        when(requestCalendar.getProperties()).thenReturn(new PropertyList<>());

        /*requestCalendar = new Calendar();
        requestCalendar.getProperties().add(Version.VERSION_2_0);
        requestCalendar.getProperties().add(CalScale.GREGORIAN);
        requestCalendar.getProperties().add(new ProdId(PRODUCT_IDENTIFIER));
        requestCalendar.getProperties().add(Method.REQUEST);
        doReturn(requestCalendar).when(basicCalendarTemplateInstaller).setUpRequestMethod();

        cancelCalendar = new Calendar();
        cancelCalendar.getProperties().add(Version.VERSION_2_0);
        cancelCalendar.getProperties().add(CalScale.GREGORIAN);
        cancelCalendar.getProperties().add(new ProdId(PRODUCT_IDENTIFIER));
        cancelCalendar.getProperties().add(Method.CANCEL);
        doReturn(cancelCalendar).when(basicCalendarTemplateInstaller).setUpCancelMethod();*/
    }

    /*@Test
    public void testCreatingSimpleCalendarTemplate() throws ParseException, IOException, URISyntaxException {
        VEvent event = new VEvent(new Date("2018-12-3001:01:01"), new Date("2019-01-30T02:02:02"), "Summary");
        Calendar expectedCalendar = new Calendar(requestCalendar);
        expectedCalendar.getComponents().add(event);

        Calendar actualCalendar = calendarTemplateCreator.createSimpleCalendarTemplate(event);

        assertEquals(expectedCalendar, actualCalendar);
    }*/

    @Test
    public void someTest(){
        Calendar calendarMock = mock(Calendar.class);
        when(calendarMock.toString()).thenReturn("Mock string");
        VEvent mockVEvent = mock(VEvent.class);


        when(creator.createSimpleCalendarTemplate(mockVEvent)).thenReturn(calendarMock);

        System.out.println(creator.createSimpleCalendarTemplate(mockVEvent).toString());
    }
}