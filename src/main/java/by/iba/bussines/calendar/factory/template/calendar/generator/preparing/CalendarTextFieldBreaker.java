package by.iba.bussines.calendar.factory.template.calendar.generator.preparing;

import org.springframework.stereotype.Component;

@Component
public class CalendarTextFieldBreaker {
    public String lineBreak(String stringField) {
        StringBuffer sourceStringBuilder = new StringBuffer(stringField);
        if (stringField.length() > 65) {
            int countOfLines = stringField.length() / 65;
            for (int i = 0; i < countOfLines; i++) {
                sourceStringBuilder.insert(65, "\n");
            }
            stringField = sourceStringBuilder.toString();
        }
        return stringField;
    }
}
