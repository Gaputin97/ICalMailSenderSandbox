package by.iba.exception;

public class CalendarSendingException extends RuntimeException {

    public CalendarSendingException() {
        super();
    }

    public CalendarSendingException(String message) {
        super(message);
    }

    public CalendarSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalendarSendingException(Throwable cause) {
        super(cause);
    }

    protected CalendarSendingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
