package by.iba.bussiness.sender.service;

import by.iba.bussiness.calendar.attendee.model.Attendee;
import by.iba.bussiness.status.send.CalendarResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SenderService {

    CalendarResponseStatus sendMeeting(HttpServletRequest request, String meetingId, List<Attendee> attendees);
}
