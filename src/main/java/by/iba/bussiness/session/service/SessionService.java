package by.iba.bussiness.session.service;

import by.iba.bussiness.session.model.Session;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SessionService {
    List<Session> getEventSessions(HttpServletRequest request, String meetingId);

    List<Session> sortAndGetEventSessions(HttpServletRequest request, String meetingId);

}
