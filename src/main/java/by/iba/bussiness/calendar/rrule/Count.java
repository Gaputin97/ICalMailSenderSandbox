package by.iba.bussiness.calendar.rrule;

public enum Count {
    DEFAULT(0), ONE_SESSION_COUNT(1);

    private int intCount;

    Count(int intCount) {
        this.intCount = intCount;
    }

    public int getIntCount() {
        return intCount;
    }

    public void setIntCount(int intCount) {
        this.intCount = intCount;
    }
}
