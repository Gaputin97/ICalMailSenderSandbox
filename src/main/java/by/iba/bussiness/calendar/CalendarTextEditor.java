package by.iba.bussiness.calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class  CalendarTextEditor {
    private static final Logger logger = LoggerFactory.getLogger(CalendarTextEditor.class);

    public String replaceColonToEqual(String method) {
        logger.debug("Replaced string: " + method);
        return method.replace("METHOD", "method")
                .replace(':', '=');
    }
}
