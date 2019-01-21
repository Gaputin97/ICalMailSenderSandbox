package by.iba.bussiness.notification;

import by.iba.bussiness.notification.service.NotificationService;
import by.iba.bussiness.sender.MailSendingResponseStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class NotificationController {
    private NotificationService senderService;

    @Autowired
    public NotificationController(NotificationService senderService) {
        this.senderService = senderService;
    }

    @ApiOperation(value = "Send calendar notifications to all learners of meeting. ")
    @RequestMapping(value = "/notification/send/{meetingId}", method = RequestMethod.GET)
    public List<MailSendingResponseStatus> sendCalendar(@PathVariable String meetingId,
                                                        HttpServletRequest request) {
        return senderService.sendCalendarToAllEnrollmentsOfMeeting(request, meetingId);
    }
}
