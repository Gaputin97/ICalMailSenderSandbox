package by.iba.bussiness.kakoetoimya.service.v1;

import by.iba.bussiness.calendar.learner.Learner;
import by.iba.bussiness.enroll.EnrollLearnerResponseStatus;
import by.iba.bussiness.enroll.service.EnrollService;
import by.iba.bussiness.kakoetoimya.ImyaResponseStatus;
import by.iba.bussiness.kakoetoimya.service.ImyaService;
import by.iba.bussiness.notification.NotificationResponseStatus;
import by.iba.bussiness.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImyaServiceImpl implements ImyaService {

    private EnrollService enrollService;
    private NotificationService notificationService;

    @Autowired
    public ImyaServiceImpl(EnrollService enrollService, NotificationService notificationService) {
        this.enrollService = enrollService;
        this.notificationService = notificationService;
    }

    @Override
    public ImyaResponseStatus imyaMethoda(HttpServletRequest request, String meetingId, List<Learner> learners) {
        ImyaResponseStatus imyaResponseStatus = new ImyaResponseStatus();
        List<EnrollLearnerResponseStatus> enrollLearnerResponseStatuses = enrollService.enrollLearners(request, meetingId, learners);
        List<NotificationResponseStatus> notificationResponseStatuses = notificationService.sendCalendarToAllEnrollmentsOfMeeting(request, meetingId);

        List<NotificationResponseStatus> filtredNotificationResponseStatus = notificationResponseStatuses.stream().filter(notification -> {
            boolean doNotificationContainLearner = learners.stream().map(Learner::getEmail).anyMatch(notification.getRecipientEmail()::equals);
            return (notification.isDelivered() || doNotificationContainLearner);
        }).collect(Collectors.toList());

        imyaResponseStatus.setEnrollLearnerResponseStatuses(enrollLearnerResponseStatuses);
        imyaResponseStatus.setNotificationResponseStatuses(filtredNotificationResponseStatus);
        return imyaResponseStatus;
    }


}
