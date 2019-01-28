package by.iba.bussiness.notification.service;

import by.iba.bussiness.sender.MailSendingResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SenderService {
    List<MailSendingResponseStatus> sendCalendarToAllEnrollmentsOfMeeting(HttpServletRequest request, String meetingId);
}
