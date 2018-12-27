package by.iba.bussiness.calendar.date.builder.single;

import by.iba.bussiness.calendar.date.model.single.SingleDateHelper;
import by.iba.bussiness.calendar.session.model.Session;
import org.springframework.stereotype.Component;

@Component
public class SimpleDateHelperBuilder {
    private Session session;

    public SimpleDateHelperBuilder setSession(Session session) {
        this.session = session;
        return this;
    }

    public SingleDateHelper build() {
        SingleDateHelper singleMeetingWrapper = new SingleDateHelper();
        singleMeetingWrapper.setSession(session);
        return singleMeetingWrapper;
    }
}
