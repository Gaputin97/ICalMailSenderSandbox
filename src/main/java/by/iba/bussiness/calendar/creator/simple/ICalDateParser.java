package by.iba.bussiness.calendar.creator.simple;

import org.springframework.stereotype.Component;

@Component
public class ICalDateParser {
    public String parseToICalDate(String date) {
        return date.replace("-", "").replace(":", "");
    }
}
