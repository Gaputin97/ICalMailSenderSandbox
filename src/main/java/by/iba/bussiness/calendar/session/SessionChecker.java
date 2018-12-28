package by.iba.bussiness.calendar.session;

import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SessionChecker {

    public boolean doAllSessionsTheSame(Meeting meeting) {
        List<TimeSlot> timeSlots = meeting.getTimeSlots();
        return timeSlots.stream().map(TimeSlot::getDuration).distinct().count() == 1;
    }
}
