package by.iba.bussiness.template;

import by.iba.bussiness.calendar.session.Session;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class TemplateTimeSlotDefiner {

    public int defineHighestIdOfSessions(List<Session> sessions) {
        return
            sessions
                .stream()
                .map(Session::getId)
                .max(Comparator.comparing(x -> x))
                .orElseThrow(NoSuchElementException::new);
    }

    public int defineLowestIdOfSessions(List<Session> sessions) {
        return
            sessions
                .stream()
                .map(Session::getId)
                .min(Comparator.comparing(x -> x))
                .orElseThrow(NoSuchElementException::new);
    }

    public Session defineSessionById(int sessionId, List<Session> sessions) {
        Optional<Session> optionalNewAppTimeSlots =
            sessions
                .stream()
                .filter(x -> x.getId() == sessionId)
                .findFirst();
        return optionalNewAppTimeSlots.orElse(null);
    }
}
