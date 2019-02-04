package by.iba.bussiness.kakoetoimya.service;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.kakoetoimya.AggregatorResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AggregatorService {

    AggregatorResponseStatus aggregateEnrollAndSend(HttpServletRequest request, String meetingId, List<Learner> learners);
}
