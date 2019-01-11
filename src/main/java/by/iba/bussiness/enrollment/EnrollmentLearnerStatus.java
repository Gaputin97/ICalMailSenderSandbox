package by.iba.bussiness.enrollment;

public class EnrollmentLearnerStatus {
    private boolean wasEnroll;
    private String message;
    private String learnerEmail;

    public EnrollmentLearnerStatus(boolean wasEnroll, String message, String learnerEmail) {
        this.wasEnroll = wasEnroll;
        this.message = message;
        this.learnerEmail = learnerEmail;
    }

    public boolean WasEnroll() {
        return wasEnroll;
    }

    public void setWasEnroll(boolean wasEnroll) {
        this.wasEnroll = wasEnroll;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLearnerEmail() {
        return learnerEmail;
    }

    public void setLearnerEmail(String learnerEmail) {
        this.learnerEmail = learnerEmail;
    }
}
