package by.iba.bussiness.aggregator.service.v1;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enroll.EnrollLearnerResponseStatus;
import by.iba.bussiness.enroll.service.EnrollService;
import by.iba.bussiness.aggregator.AggregatorResponseStatus;
import by.iba.bussiness.aggregator.service.AggregatorService;
import by.iba.bussiness.notification.NotificationResponseStatus;
import by.iba.bussiness.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AggregatorServiceImpl implements AggregatorService {

    private EnrollService enrollService;
    private NotificationService notificationService;

    @Autowired
    public AggregatorServiceImpl(EnrollService enrollService, NotificationService notificationService) {
        this.enrollService = enrollService;
        this.notificationService = notificationService;
    }

    @Override
    public AggregatorResponseStatus aggregateEnrollAndSend(HttpServletRequest request, String meetingId, List<Learner> learners) {
        AggregatorResponseStatus aggregatorResponseStatus = new AggregatorResponseStatus();
        List<EnrollLearnerResponseStatus> enrollLearnerResponseStatuses = enrollService.enrollLearners(request, meetingId, learners);
        List<NotificationResponseStatus> notificationResponseStatuses = notificationService.sendCalendarToAllEnrollmentsOfMeeting(request, meetingId);
        List<NotificationResponseStatus> filteredNotificationResponseStatus = notificationResponseStatuses.stream().filter(notification -> {
            boolean doNotificationContainLearner = learners.stream().map(Learner::getEmail).anyMatch(notification.getRecipientEmail()::equals);
            return (notification.isDelivered() || doNotificationContainLearner);
        }).collect(Collectors.toList());

        aggregatorResponseStatus.setEnrollLearnerResponseStatuses(enrollLearnerResponseStatuses);
        aggregatorResponseStatus.setNotificationResponseStatuses(filteredNotificationResponseStatus);
        return aggregatorResponseStatus;
    }


}
