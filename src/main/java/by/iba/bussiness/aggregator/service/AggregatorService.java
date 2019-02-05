package by.iba.bussiness.aggregator.service;

import by.iba.bussiness.aggregator.AggregatorResponseStatus;
import by.iba.bussiness.calendar.learner.Learner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AggregatorService {
    AggregatorResponseStatus enrollLearnerAndSendNotification(HttpServletRequest request, String meetingId, List<Learner> learners);
}
