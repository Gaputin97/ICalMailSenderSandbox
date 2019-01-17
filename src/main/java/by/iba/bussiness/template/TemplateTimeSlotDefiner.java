package by.iba.bussiness.template;

import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class TemplateTimeSlotDefiner {
    public int defineHighestIdOfSessions(List<Session> timeSlots) {
        return timeSlots.stream().map(Session::getId).mapToInt(x -> x).max().orElseThrow(NoSuchElementException::new);
    }

    public int defineLowestIdOfSessions(List<Session> timeSlots) {
        return timeSlots.stream().map(Session::getId).mapToInt(x -> x).min().orElseThrow(NoSuchElementException::new);
    }
}
