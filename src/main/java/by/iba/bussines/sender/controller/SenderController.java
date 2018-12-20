package by.iba.bussines.sender.controller;

import by.iba.bussines.enrollment.model.Enrollment;
import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.service.MeetingService;
import by.iba.bussines.status.insert.InsertStatus;
import by.iba.bussines.status.send.CalendarSendingStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class SenderController {
    @Autowired
    private MeetingService meetingService;

    @ApiOperation(value = "Send calendar templates for non existing recipients", response = Enrollment.class)
    @RequestMapping(value = "/send/meeting", method = RequestMethod.POST)
    public CalendarSendingStatus sendInvitationTemplatesToRecipients(HttpServletRequest request,
                                                                     @RequestBody String meetingId,
                                                                     @RequestBody String[] recipientList) {
        Meeting meeting = meetingService.getMeetingById(request, meetingId);

        return new CalendarSendingStatus("All templates was successfully sanded");
    }
}
