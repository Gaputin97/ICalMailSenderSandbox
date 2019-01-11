package by.iba.bussiness.enrollment;

import by.iba.bussiness.calendar.attendee.Learner;
import by.iba.bussiness.enrollment.repository.EnrollmentRepository;
import by.iba.bussiness.enrollment.service.EnrollmentService;

import by.iba.bussiness.sender.ResponseStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.List;

@RestController
public class EnrollmentController {
    private EnrollmentService enrollmentService;
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService, EnrollmentRepository enrollmentRepository) {
        this.enrollmentService = enrollmentService;
        this.enrollmentRepository = enrollmentRepository;
    }

    @ApiOperation(value = "Get enrollment by email and meetingId", response = Enrollment.class)
    @RequestMapping(value = "/enrollment/get/{parentId}/{userEmail}", method = RequestMethod.GET)
    public Enrollment getEnrollmentByEmailAndMeetingId(@PathVariable(value = "parentId") BigInteger parentId,
                                                       @PathVariable(value = "userEmail") String userEmail,
                                                       HttpServletRequest request) {
        return enrollmentService.getEnrollmentByEmailAndParentId(request, parentId, userEmail);
    }

    @ApiOperation(value = "Get enrollment by email and meetingId from local database", response = Enrollment.class)
    @RequestMapping(value = "/enrollment/local/get/{parentId}/{userEmail}", method = RequestMethod.GET)
    public Enrollment getLocalEnrollmentByEmailAndMeetingId(@PathVariable(value = "parentId") BigInteger parentId,
                                                            @PathVariable(value = "userEmail") String userEmail) {
        return enrollmentRepository.getByEmailAndParentId(parentId, userEmail);
    }

    @ApiOperation(value = "Save enrollment to local database", response = Enrollment.class)
    @RequestMapping(value = "/enrollment/save", method = RequestMethod.POST)
    public Enrollment saveEnrollment(@RequestBody Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @ApiOperation(value = "Enroll users to meeting")
    @RequestMapping(value = "/enrollment/add/{meetingId}", method = RequestMethod.POST)
    public void enrollUsers(@PathVariable String meetingId,
                            @RequestBody List<Learner> learners,
                            HttpServletRequest request) {
        enrollmentService.enrollLearners(request, meetingId, learners);
    }

    @ApiOperation(value = "Send calendar to existing users")
    @RequestMapping(value = "/enrollment/send/{meetingId}", method = RequestMethod.GET)
    public List<ResponseStatus> sendCalendar(@PathVariable String meetingId,
                                             HttpServletRequest request) {
        return enrollmentService.sendCalendarToAllEnrollmentsOfMeeting(request, meetingId);
    }
}