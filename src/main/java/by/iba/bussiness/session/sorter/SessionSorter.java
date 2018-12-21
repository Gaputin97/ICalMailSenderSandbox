package by.iba.bussiness.session.sorter;

import by.iba.bussiness.session.model.Session;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class SessionSorter {

    public List<Session> sortAndGetSessions(List<Session> sessions) {
        Collections.sort(sessions);
        return sessions;
    }
}
