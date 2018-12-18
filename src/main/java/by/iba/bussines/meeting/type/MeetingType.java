package by.iba.bussines.meeting.type;

import net.fortuna.ical4j.model.property.Method;

public enum MeetingType {
    SINGLE(Method.REQUEST), COMPLEX(Method.PUBLISH), RECURRENCE(Method.REQUEST);

    private Method method;

    MeetingType(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
