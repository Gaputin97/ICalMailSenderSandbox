package by.iba.bussines.enrollment.controller;

import by.iba.bussines.enrollment.model.Enrollment;
import by.iba.bussines.enrollment.service.EnrollmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @ApiOperation(value = "Get enrollment by email and meetingId", response = Enrollment.class)
    @RequestMapping(value = "/enrollment/get/{meetingId}/{email}", method = RequestMethod.GET)
    public Enrollment getEnrollmentByEmailAndMeetingId(@PathVariable(value = "meetingId") String meetingId,
                                                       @PathVariable(value = "email") String email,
                                                       HttpServletRequest request) {
        return enrollmentService.getEnrollmentByEmailAndMeeting(request, meetingId, email);
    }
}
