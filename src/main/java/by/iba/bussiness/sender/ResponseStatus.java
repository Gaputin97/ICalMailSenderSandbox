package by.iba.bussiness.sender;

public class ResponseStatus {
    private boolean isDelivered;
    private String recipientName;
    private String recipientEmail;

    public ResponseStatus(boolean isDelivered, String recipientName, String recipientEmail) {
        this.isDelivered = isDelivered;
        this.recipientName = recipientName;
        this.recipientEmail = recipientEmail;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }
}