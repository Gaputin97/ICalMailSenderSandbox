package by.iba.bussines.calendar.service.recurrence;

import by.iba.bussines.meeting.model.Meeting;
import net.fortuna.ical4j.model.Calendar;

public interface RecurrenceMeeting {
    Calendar createRecurrenceCalendarTemplate(Meeting meeting);
}
