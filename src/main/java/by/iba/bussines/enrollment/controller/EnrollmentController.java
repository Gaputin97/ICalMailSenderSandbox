package by.iba.bussines.enrollment.controller;

import by.iba.bussines.enrollment.model.Enrollment;
import by.iba.bussines.enrollment.service.EnrollmentService;
import by.iba.bussines.status.insert.InsertStatus;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;

    @ApiOperation(value = "Get enrollment by email and meetingId", response = Enrollment.class)
    @RequestMapping(value = "/enrollment/get/{parentId}/{userEmail}", method = RequestMethod.GET)
    public Enrollment getEnrollmentByEmailAndMeetingId(@PathVariable(value = "parentId") String parentId,
                                                       @PathVariable(value = "userEmail") String userEmail,
                                                       HttpServletRequest request) {
        return enrollmentService.getEnrollmentByEmailAndMeetingId(request, parentId, userEmail);
    }

    @ApiOperation(value = "Get enrollment by email and meetingId from local database", response = Enrollment.class)
    @RequestMapping(value = "/enrollment/local/get/{parentId}/{userEmail}", method = RequestMethod.GET)
    public Enrollment getLocalEnrollmentByEmailAndMeetingId(@PathVariable(value = "parentId") String parentId,
                                                            @PathVariable(value = "userEmail") String userEmail) {
        return enrollmentService.getLocalEnrollmentByEmailAndMeetingId(parentId, userEmail);
    }

    @ApiOperation(value = "Save enrollment to local database", response = Enrollment.class)
    @RequestMapping(value = "/enrollment/save", method = RequestMethod.POST)
    public InsertStatus saveEnrollment(@RequestBody Enrollment enrollment) {
        return enrollmentService.saveEnrollment(enrollment);
    }
}
