package by.iba.bussiness.meeting;

import net.fortuna.ical4j.model.property.Method;

public enum MeetingType {
    SINGLE(Method.REQUEST), RECURRENCE(Method.REQUEST), COMPLEX(Method.PUBLISH);

    private Method method;

    MeetingType(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
