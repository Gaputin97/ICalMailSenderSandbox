package by.iba.bussiness.aggregator;

import by.iba.bussiness.enroll.EnrollLearnerResponseStatus;
import by.iba.bussiness.notification.NotificationResponseStatus;

import java.util.ArrayList;
import java.util.List;

public class AggregatorResponseStatus {
    private List<EnrollLearnerResponseStatus> enrollLearnerResponseStatuses;
    private List<NotificationResponseStatus> notificationResponseStatuses;

    public AggregatorResponseStatus() {
        enrollLearnerResponseStatuses = new ArrayList<>();
        notificationResponseStatuses = new ArrayList<>();
    }

    public List<EnrollLearnerResponseStatus> getEnrollLearnerResponseStatuses() {
        return enrollLearnerResponseStatuses;
    }

    public void setEnrollLearnerResponseStatuses(List<EnrollLearnerResponseStatus> enrollLearnerResponseStatuses) {
        this.enrollLearnerResponseStatuses = enrollLearnerResponseStatuses;
    }

    public List<NotificationResponseStatus> getNotificationResponseStatuses() {
        return notificationResponseStatuses;
    }

    public void setNotificationResponseStatuses(List<NotificationResponseStatus> notificationResponseStatuses) {
        this.notificationResponseStatuses = notificationResponseStatuses;
    }
}
