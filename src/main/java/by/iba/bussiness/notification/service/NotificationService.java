package by.iba.bussiness.notification.service;

import by.iba.bussiness.notification.NotificationResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface NotificationService {

    List<NotificationResponseStatus> sendCalendarToAllEnrollmentsOfMeeting(HttpServletRequest request, String meetingId);
}
