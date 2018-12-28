package by.iba.bussiness.calendar.creator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CalendarTextEditor {
    private static final  Logger logger = LoggerFactory.getLogger(CalendarTextEditor.class);
    public String breakLine(String stringField) {
        StringBuffer sourceStringBuilder = new StringBuffer(stringField);
        if (stringField.length() > 65) {
            int countOfLines = stringField.length() / 65;
            for (int i = 0; i < countOfLines; i++) {
                sourceStringBuilder.insert(64, "\n");
            }
            stringField = sourceStringBuilder.toString();
        }
        logger.debug("String: " + stringField);
        return stringField;
    }

    public String replaceColonToEqual(String method) {
        logger.debug("Replaced string: " + method);
        return method.replace(':', '=');
    }

    public String editUserEmail(String email) {
        if (email.substring(0, 7).equals("mailto:")) {
            email = email.substring(7);
        }
        logger.debug("Recipient string: " + email);
        return email;
    }
}
