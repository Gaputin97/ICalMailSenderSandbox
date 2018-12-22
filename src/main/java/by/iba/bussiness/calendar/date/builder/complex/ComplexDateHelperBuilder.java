package by.iba.bussiness.calendar.date.builder.complex;

import by.iba.bussiness.calendar.date.model.complex.ComplexDateHelper;
import by.iba.bussiness.calendar.session.model.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComplexDateHelperBuilder {
    private List<Session> sessions;

    public ComplexDateHelperBuilder setSessions(List<Session> sessions) {
        this.sessions = sessions;
        return this;
    }

    public ComplexDateHelper build() {
        ComplexDateHelper complexDateHelper = new ComplexDateHelper();
        complexDateHelper.setSessions(sessions);
        return complexDateHelper;
    }


}
