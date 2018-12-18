package by.iba.bussines.calendar.service.single;

import by.iba.bussines.meeting.model.Meeting;
import net.fortuna.ical4j.model.Calendar;

public interface SingleMeeting {
    Calendar createSingleCalendarTemplate(Meeting meeting);
}
