package by.iba.bussiness.calendar.rrule;

public enum RruleCount {
    ZERO(0), ONE_SESSION(1);

    private int intCount;

    RruleCount(int intCount) {
        this.intCount = intCount;
    }

    public int getIntCount() {
        return intCount;
    }

    public void setIntCount(int intCount) {
        this.intCount = intCount;
    }
}
