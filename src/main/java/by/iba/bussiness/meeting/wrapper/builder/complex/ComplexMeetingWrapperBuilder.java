package by.iba.bussiness.meeting.wrapper.builder.complex;

import by.iba.bussiness.meeting.wrapper.builder.AbstractMeetingWrapperBuilder;
import by.iba.bussiness.meeting.wrapper.model.complex.ComplexMeetingWrapper;
import by.iba.bussiness.session.model.Session;

import java.util.List;

public class ComplexMeetingWrapperBuilder extends AbstractMeetingWrapperBuilder<ComplexMeetingWrapperBuilder> {
    private List<Session> sessions;

    public ComplexMeetingWrapperBuilder(ComplexMeetingWrapperBuilder builderClass) {
        super(builderClass);
    }

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
