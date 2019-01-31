package by.iba.bussiness.enroll;

public class EnrollLearnerResponseStatus {
    private boolean wasEnroll;
    private String message;
    private String learnerEmail;

    public EnrollLearnerResponseStatus(boolean wasEnroll, String message, String learnerEmail) {
        this.wasEnroll = wasEnroll;
        this.message = message;
        this.learnerEmail = learnerEmail;
    }

    public boolean isWasEnroll() {
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
