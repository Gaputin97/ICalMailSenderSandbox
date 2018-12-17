package by.iba.bussines.session.parser;

import by.iba.bussines.session.constants.SessionConstants;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SessionParser {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(SessionConstants.DATE_FORMAT);

    public Date stringToDate(String date) {
        Date returnedDate;
        try {
            returnedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return returnedDate;
    }
}
