package by.iba.bussines.session.service.parser;

import by.iba.bussines.sender.algorithm.constants.DateConstants;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SessionParser {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(DateConstants.DATE_FORMAT);

    public static Date stringToDate(String date) {
        Date returnedDate;
        try {
            returnedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return returnedDate;
    }
}
