package by.iba.bussiness.template;

import by.iba.bussiness.meeting.timeslot.TimeSlot;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class TemplateTimeSlotDefiner {

    public int defineHighestIdOfTimeSlots(List<TimeSlot> timeSlots) {
        return timeSlots.stream().map(TimeSlot::getId).mapToInt(x -> x).max().orElseThrow(NoSuchElementException::new);
    }

    public int defineLowestIdOfTimeSlots(List<TimeSlot> timeSlots) {
        return timeSlots.stream().map(TimeSlot::getId).mapToInt(x -> x).min().orElseThrow(NoSuchElementException::new);
    }

    public TimeSlot defineTimeSlotWithId(int timeSlotId, List<TimeSlot> timeSlots) {
        Optional<TimeSlot> optionalNewAppTimeSlots = timeSlots.stream().filter(x -> x.getId() == timeSlotId).findFirst();
        return optionalNewAppTimeSlots.orElse(null);
    }
}
