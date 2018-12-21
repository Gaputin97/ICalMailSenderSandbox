package by.iba.bussiness.meeting.wrapper.builder.single;

import by.iba.bussiness.meeting.wrapper.model.single.SingleMeetingWrapper;
import by.iba.bussiness.session.model.Session;
import org.springframework.stereotype.Component;

@Component
public class SingleMeetingWrapperBuilder {
    private Session session;

    public SingleMeetingWrapperBuilder setSession(Session session) {
        this.session = session;
        return this;
    }

    public SingleMeetingWrapper build() {
        SingleMeetingWrapper singleMeetingWrapper = new SingleMeetingWrapper();
        singleMeetingWrapper.setSession(session);
        return singleMeetingWrapper;
    }
}
