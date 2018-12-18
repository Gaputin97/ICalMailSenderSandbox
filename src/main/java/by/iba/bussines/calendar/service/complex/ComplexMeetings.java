package by.iba.bussines.calendar.service.complex;

import by.iba.bussines.meeting.model.Meeting;
import net.fortuna.ical4j.model.Calendar;

public interface ComplexMeetings {
    Calendar createComplexCalendarTemplate(Meeting meeting);
}
