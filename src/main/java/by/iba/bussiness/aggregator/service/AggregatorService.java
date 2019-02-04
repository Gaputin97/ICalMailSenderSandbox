package by.iba.bussiness.aggregator.service;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.aggregator.AggregatorResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AggregatorService {
    AggregatorResponseStatus aggregateEnrollAndSend(HttpServletRequest request, String meetingId, List<Learner> learners);
}
