package by.iba.bussiness.sender.service;

import by.iba.bussiness.calendar.email.Email;

import javax.servlet.http.HttpServletRequest;

public interface SenderService {
    void sendMeeting(HttpServletRequest request, String meetingId, Email emailList);
}
