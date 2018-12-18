//package by.iba.bussines.sender.service.controller;
//
//import by.iba.bussines.enrollment.model.Enrollment;
//import by.iba.bussines.status.send.CalendarSendingStatus;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class SenderController {
//
//    @ApiOperation(value = "Send message ", response = Enrollment.class)
//    @RequestMapping(value = "/sendCalendar/", method = RequestMethod.GET)
//    public CalendarSendingStatus sendCalendar(@RequestBody String parentId,
//                                      @PathVariable(value = "userEmail") String userEmail) {
//        return enrollmentService.getLocalEnrollmentByEmailAndMeetingId(parentId, userEmail);
//    }
//}
