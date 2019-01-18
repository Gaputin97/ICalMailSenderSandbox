package by.iba.bussiness.enroll;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enroll.service.EnrollService;
import by.iba.bussiness.enrollment.EnrollRequestWrapper;
import by.iba.bussiness.enrollment.EnrollmentLearnerStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class EnrollController {
    private EnrollService enrollService;

    @Autowired
    public EnrollController(EnrollService enrollmentService) {
        this.enrollService = enrollmentService;
    }

    @ApiOperation(value = "Enroll learners to meeting.")
    @RequestMapping(value = "/enroll/", method = RequestMethod.POST)
    public List<EnrollmentLearnerStatus> enrollLearners(@RequestBody EnrollRequestWrapper enrollRequestWrapper,
                                                        HttpServletRequest request) {
        String meetingId = enrollRequestWrapper.getMeetingId();
        List<Learner> learners = enrollRequestWrapper.getLearners();
        return enrollService.enrollLearners(request, meetingId, learners);
    }
}
