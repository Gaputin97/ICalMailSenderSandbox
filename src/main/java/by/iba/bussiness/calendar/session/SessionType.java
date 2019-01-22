package by.iba.bussiness.calendar.session;

public enum SessionType {
    NEW(" (new date)"), NOT_CHANGED(" (was not changed)"), RESCHEDULED(" (rescheduled)"), DELETED(" (deleted)");

    private String stringType;

    SessionType(String stringType) {
        this.stringType = stringType;
    }

    public String getStringType() {
        return stringType;
    }

    public void setStringType(String stringType) {
        this.stringType = stringType;
    }
}
