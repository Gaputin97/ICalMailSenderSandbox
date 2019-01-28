package by.iba.bussiness.calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CalendarTextEditor {
    private static final Logger logger = LoggerFactory.getLogger(CalendarTextEditor.class);

    public String replaceColonToEqual(String method) {
        logger.debug("Replaced string: " + method);
        return method.replace("METHOD", "method")
                .replace(':', '=');
    }

    public String editUserEmail(String email) {
        if (email.substring(0, 7).equals("mailto:")) {
            email = email.substring(7);
        }
        logger.debug("Recipient string: " + email);
        return email;
    }
}