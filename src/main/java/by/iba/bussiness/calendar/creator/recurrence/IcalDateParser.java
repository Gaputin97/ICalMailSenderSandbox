package by.iba.bussiness.calendar.creator.recurrence;

import org.springframework.stereotype.Component;

@Component
public class IcalDateParser {
    public String parseToICalDate(String date) {
        String withoutOne = date.replace("-", "");
        String withoutBoth = withoutOne.replace(":", "");
        return withoutBoth;
    }
}
