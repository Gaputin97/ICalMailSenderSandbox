package by.iba.bussiness.response;

public class CalendarSendingResponse {

    private boolean isSended;

    public CalendarSendingResponse(boolean isSended) {
        this.isSended = isSended;
    }

    public boolean isSended() {
        return isSended;
    }

    public void setIsSended(boolean isSended) {
        this.isSended = isSended;
    }
}
