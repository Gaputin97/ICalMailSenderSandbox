package by.iba.bussiness.sender;

public class MailSendingResponseStatus {
    private boolean isDelivered;
    private String message;
    private String recipientEmail;

    public MailSendingResponseStatus(boolean isDelivered, String message, String recipientEmail) {
        this.isDelivered = isDelivered;
        this.message = message;
        this.recipientEmail = recipientEmail;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }
}