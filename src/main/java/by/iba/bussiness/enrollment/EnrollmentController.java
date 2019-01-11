package by.iba.bussiness.enrollment;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enrollment.service.EnrollmentService;
import by.iba.bussiness.sender.MailSendingResponseStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class EnrollmentController {
    private EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @ApiOperation(value = "Enroll learners to meeting. ")
    @RequestMapping(value = "/enrollment/learner", method = RequestMethod.POST)
    public List<EnrollmentLearnerStatus> enrollLearners(@RequestBody EnrollmentRequestWrapper enrollmentRequestWrapper,
                                                        HttpServletRequest request) {
        String meetingId = enrollmentRequestWrapper.getMeetingId();
        List<Learner> learners = enrollmentRequestWrapper.getLearners();
        return enrollmentService.enrollLearners(request, meetingId, learners);
    }

    @ApiOperation(value = "Send calendar notifications to all learners of meeting. ")
    @RequestMapping(value = "/enrollment/send/{meetingId}", method = RequestMethod.GET)
    public List<MailSendingResponseStatus> sendCalendar(@PathVariable  String meetingId,
                                                        HttpServletRequest request) {
        return enrollmentService.sendCalendarToAllEnrollmentsOfMeeting(request, meetingId);
    }
}