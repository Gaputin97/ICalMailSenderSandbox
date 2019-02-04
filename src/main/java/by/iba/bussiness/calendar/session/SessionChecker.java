package by.iba.bussiness.calendar.session;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionChecker {
    public boolean areAllSessionsTheSame(List<Session> timeSlots) {
        return timeSlots.stream().map(Session::getDuration).distinct().count() == 1;
    }
}
