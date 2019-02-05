package by.iba.bussiness.aggregator;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enrollment.EnrollRequestWrapper;
import by.iba.bussiness.aggregator.service.AggregatorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AggregatorController {
    private AggregatorService aggregatorService;

    @Autowired
    public AggregatorController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @ApiOperation(value = "Enroll users and send notifications. ")
    @RequestMapping(value = "/aggregator/", method = RequestMethod.POST)
    public AggregatorResponseStatus enrollLearnersAndSendNotifications(@RequestBody EnrollRequestWrapper enrollRequestWrapper, HttpServletRequest request) {
        String meetingId = enrollRequestWrapper.getMeetingId();
        List<Learner> learners = enrollRequestWrapper.getLearners();
        return aggregatorService.enrollLearnerAndSendNotification(request, meetingId, learners);
    }
}
