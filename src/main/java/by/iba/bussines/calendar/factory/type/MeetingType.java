package by.iba.bussines.calendar.factory.type;

import net.fortuna.ical4j.model.property.Method;

public enum MeetingType {
    SIMPLE(Method.REQUEST), CANCEL(Method.CANCEL), COMPLEX(Method.PUBLISH);

    private Method method;

    MeetingType(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }
}
