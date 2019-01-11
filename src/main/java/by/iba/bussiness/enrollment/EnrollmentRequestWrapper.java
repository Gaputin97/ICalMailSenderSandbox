package by.iba.bussiness.enrollment;

import by.iba.bussiness.calendar.learner.Learner;

import java.util.List;

public class EnrollmentRequestWrapper {
    private String meetingId;
    private List<Learner> learners;

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public List<Learner> getLearners() {
        return learners;
    }

    public void setLearners(List<Learner> learners) {
        this.learners = learners;
    }
}
