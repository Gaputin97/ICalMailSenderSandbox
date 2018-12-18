package by.iba.bussines.calendar.factory.text.preparing;

import org.springframework.stereotype.Component;

@Component
public class CalendarTextPrepare {
    private String summary;
    private String description;

    public CalendarTextPrepare(String summary, String description) {
        this.summary = lineBreak(summary);
        this.description = lineBreak(description);
    }

    private String lineBreak(String stringField) {
        StringBuffer sourceStringBuilder = new StringBuffer(stringField);
        if(stringField.length() > 65) {
            int countOfLines = stringField.length() / 65;
            for (int i = 0; i < countOfLines; i++) {
                sourceStringBuilder.insert(65, "\n");
            }
            stringField = sourceStringBuilder.toString();
        }
        return stringField;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }
}
