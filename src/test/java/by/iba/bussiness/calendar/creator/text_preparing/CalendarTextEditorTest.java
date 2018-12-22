package by.iba.bussiness.calendar.creator.text_preparing;

import by.iba.configuration.ApplicationConfiguration;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@TestPropertySource(locations="classpath:application.properties")
public class CalendarTextEditorTest {

    @Autowired
    CalendarTextEditor calendarTextEditor;

    String sourceString = "avavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavava" +
            "avavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavava";
    String rightString = "avavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavavaavava\nnavavaavavaavava" +
            "avavaavavaavavaavavaavavaavavaavavaavavaavavaavava\nnavavaavavaavavaavava";



}
