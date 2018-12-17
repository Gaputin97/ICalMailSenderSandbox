package by.iba.bussines.session.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface SessionService {
    List<Date> getEventSessions(HttpServletRequest request, String meetingId);
}
