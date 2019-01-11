package by.iba.bussiness.enrollment.service;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enrollment.EnrollmentLearnerStatus;
import by.iba.bussiness.sender.MailSendingResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EnrollmentService {
    List<EnrollmentLearnerStatus> enrollLearners(HttpServletRequest request, String meetingId, List<Learner> learners);
    List<MailSendingResponseStatus> sendCalendarToAllEnrollmentsOfMeeting(HttpServletRequest request, String meetingId);
}
