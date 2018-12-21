package by.iba.bussiness.calendar.creator.type.recurrence.parser;

import org.springframework.stereotype.Component;

@Component
public class IcalDateParser {
    public String parseToIcalDate(String date) {
        String withoutOne = date.replace("-", "");
        String withoutBoth = withoutOne.replace(":", "");
        return withoutBoth;

    }
}
