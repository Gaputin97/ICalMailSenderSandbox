package by.iba.bussiness.calendar.session;

import by.iba.bussiness.meeting.timeslot.TimeSlot;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionChecker {

    public boolean doAllSessionsTheSame(List<Session> timeSlots) {
        return timeSlots.stream().map(Session::getDuration).distinct().count() == 1;
    }
}
