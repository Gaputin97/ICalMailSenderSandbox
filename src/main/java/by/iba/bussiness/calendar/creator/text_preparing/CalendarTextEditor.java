package by.iba.bussiness.calendar.creator.text_preparing;

import org.springframework.stereotype.Component;

@Component
public class CalendarTextEditor {
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

    public String colonReplace(String method) {
        return method.replace(':', '=');
    }

    public String userEmailEdit(String email) {
        if(email.substring(0, 7).equals("mailto:")) {
            email = email.substring(7);
        }
        return email;
    }
}
