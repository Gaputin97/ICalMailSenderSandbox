package by.iba.bussiness.calendar;

import org.springframework.stereotype.Component;

@Component
public class  CalendarTextEditor {

    public String replaceColonToEqual(String method) {
        return method
                .replace("METHOD", "method")
                .replace(':', '=');
    }
}
