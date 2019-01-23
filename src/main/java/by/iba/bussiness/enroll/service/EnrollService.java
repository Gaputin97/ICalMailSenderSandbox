package by.iba.bussiness.enroll.service;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enroll.EnrollLearnerStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EnrollService {
    List<EnrollLearnerStatus> enrollLearners(HttpServletRequest request, String meetingId, List<Learner> learners);
}
