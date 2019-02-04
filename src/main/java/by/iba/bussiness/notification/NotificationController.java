package by.iba.bussiness.notification;

import by.iba.bussiness.notification.service.NotificationService;
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

    private NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ApiOperation(value = "Send calendar notifications to all learners of meeting. ")
    @RequestMapping(value = "/notification/send/{meetingId}", method = RequestMethod.GET)
    public List<NotificationResponseStatus> sendCalendar(@PathVariable String meetingId,
                                                         HttpServletRequest request) {
        return notificationService.sendCalendarToAllEnrollmentsOfMeeting(request, meetingId);
    }
}
