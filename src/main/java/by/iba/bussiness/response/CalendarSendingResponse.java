package by.iba.bussiness.response;

public class CalendarSendingResponse {

    private boolean isSanded;
    private String message;

    public CalendarSendingResponse(boolean isSanded, String message) {
        this.isSanded = isSanded;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSanded() {
        return isSanded;
    }

    public void setIsSanded(boolean isSanded) {
        this.isSanded = isSanded;
    }
}
