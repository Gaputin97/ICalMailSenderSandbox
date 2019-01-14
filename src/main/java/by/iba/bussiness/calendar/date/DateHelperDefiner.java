package by.iba.bussiness.calendar.date;

import by.iba.bussiness.calendar.date.builder.ComplexDateHelperBuilder;
import by.iba.bussiness.calendar.date.builder.RecurrenceDateHelperBuilder;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.definer.RruleDefiner;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionChecker;
import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DateHelperDefiner {
    private static final Logger logger = LoggerFactory.getLogger(DateHelperDefiner.class);
    private SessionParser sessionParser;
    private RruleDefiner rruleDefiner;
    private SessionChecker sessionChecker;

    @Autowired
    public DateHelperDefiner(SessionParser sessionParser,
                             RruleDefiner rruleDefiner,
                             SessionChecker sessionChecker) {
        this.sessionParser = sessionParser;
        this.rruleDefiner = rruleDefiner;
        this.sessionChecker = sessionChecker;
    }

    public DateHelper defineDateHelper(List<TimeSlot> timeSlots) {
        DateHelper dateHelper;
        List<Session> sessions = sessionParser.timeSlotListToSessionList(timeSlots);
        if (sessionChecker.doAllSessionsTheSame(timeSlots)) {
            Rrule rrule = rruleDefiner.defineRrule(sessions);
            dateHelper = new RecurrenceDateHelperBuilder()
                    .setRrule(rrule)
                    .build();
        } else {
            dateHelper = new ComplexDateHelperBuilder()
                    .setSessionList(sessions)
                    .build();
        }
        return dateHelper;
    }

}