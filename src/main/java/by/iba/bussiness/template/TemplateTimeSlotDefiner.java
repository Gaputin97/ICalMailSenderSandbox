package by.iba.bussiness.template;

import by.iba.bussiness.meeting.timeslot.TimeSlot;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class TemplateTimeSlotDefiner {

    public int defineHighestIdOfTimeSlots(List<TimeSlot> timeSlots) {
        return timeSlots.stream().map(TimeSlot::getId).mapToInt(x -> x).max().orElseThrow(NoSuchElementException::new);
    }
}
