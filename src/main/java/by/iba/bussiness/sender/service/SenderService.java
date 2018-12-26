package by.iba.bussiness.sender.service;

import by.iba.bussiness.calendar.attendee.model.Attendee;
import by.iba.bussiness.status.send.CalendarSendingStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SenderService {

    CalendarSendingStatus sendMeeting(HttpServletRequest request, String meetingId, List<Attendee> attendees);
}
