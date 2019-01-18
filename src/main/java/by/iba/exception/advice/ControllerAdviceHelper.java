package by.iba.exception.advice;

public class ControllerAdviceHelper {
    private String message;

    public ControllerAdviceHelper(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
