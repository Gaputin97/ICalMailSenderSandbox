package by.iba.bussiness.aggregator;

import by.iba.bussiness.aggregator.service.AggregatorService;
import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enrollment.EnrollRequestWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AggregatorController {
    private AggregatorService aggregatorService;

    @Autowired
    public AggregatorController(AggregatorService aggregatorService) {
        this.aggregatorService = aggregatorService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @ApiOperation(value = "Enroll users and send notifications. ")
    @RequestMapping(value = "/aggregator/", method = RequestMethod.POST)
    public AggregatorResponseStatus enrollLearnersAndSendNotifications(@RequestBody EnrollRequestWrapper enrollRequestWrapper, HttpServletRequest request) {
        String meetingId = enrollRequestWrapper.getMeetingId();
        List<Learner> learners = enrollRequestWrapper.getLearners();
        return aggregatorService.enrollLearnerAndSendNotification(request, meetingId, learners);
    }
}
