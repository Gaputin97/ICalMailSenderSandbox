package by.iba.bussiness.meeting.wrapper.builder.complex;

import by.iba.bussiness.meeting.wrapper.model.complex.ComplexMeetingWrapper;
import by.iba.bussiness.session.model.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplexMeetingWrapperBuilder {
    private List<Session> sessions;



    public ComplexMeetingWrapperBuilder setSessions(List<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public ComplexMeetingWrapper build() {
        ComplexMeetingWrapper complexMeetingWrapper = new ComplexMeetingWrapper();
        complexMeetingWrapper.setSessions(sessions);
        return complexMeetingWrapper;
    }


}
