package by.iba.exception;

public class CalendarException extends RuntimeException {

    public CalendarException(String message) {
        super(message);
    }

    public CalendarException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalendarException(Throwable cause) {
        super(cause);
    }

    public CalendarException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
