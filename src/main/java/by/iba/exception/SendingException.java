package by.iba.exception;

public class SendingException extends RuntimeException {

    public SendingException() {
        super();
    }

    public SendingException(String message) {
        super(message);
    }

    public SendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendingException(Throwable cause) {
        super(cause);
    }

    protected SendingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
