package by.iba.bussiness.response;

public class CalendarSendingResponse {

    private boolean isSended;
    private String message;

    public CalendarSendingResponse(boolean isSended, String message) {
        this.isSended = isSended;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSended() {
        return isSended;
    }

    public void setIsSended(boolean isSended) {
        this.isSended = isSended;
    }
}
