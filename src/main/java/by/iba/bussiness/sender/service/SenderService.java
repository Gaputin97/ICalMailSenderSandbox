package by.iba.bussiness.sender.service;

import by.iba.bussiness.calendar.attendee.model.Attendee;
import by.iba.bussiness.response.CalendarSendingResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SenderService {

    CalendarSendingResponse sendMeeting(HttpServletRequest request, String meetingId, List<Attendee> attendees);
}
