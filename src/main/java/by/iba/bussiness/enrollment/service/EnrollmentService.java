package by.iba.bussiness.enrollment.service;

import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.enrollment.Enrollment;
import by.iba.bussiness.sender.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

public interface EnrollmentService {
    Enrollment getEnrollmentByEmailAndParentId(HttpServletRequest request, BigInteger parentId, String email);
    List<Enrollment> getEnrollmentsByParentId(HttpServletRequest request, BigInteger parentId);
    void enrollLearners(HttpServletRequest request, String meetingId, List<Learner> learners);
    List<ResponseStatus> sendCalendarToAllEnrollmentsOfMeeting(HttpServletRequest request, String meetingId);
}
