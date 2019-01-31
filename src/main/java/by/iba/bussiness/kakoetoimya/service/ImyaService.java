package by.iba.bussiness.kakoetoimya.service;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.kakoetoimya.ImyaResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ImyaService {

    ImyaResponseStatus imyaMethoda(HttpServletRequest request, String meetingId, List<Learner> learners);
}
