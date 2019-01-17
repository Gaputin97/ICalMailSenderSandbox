package by.iba.bussiness.calendar.date.helper;

import by.iba.bussiness.calendar.date.helper.builder.ComplexDateHelperBuilder;
import by.iba.bussiness.calendar.date.helper.builder.RecurrenceDateHelperBuilder;
import by.iba.bussiness.calendar.date.helper.model.DateHelper;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.definer.RruleDefiner;
import by.iba.bussiness.calendar.rrule.frequence.Frequency;
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
    private RruleDefiner rruleDefiner;
    private SessionChecker sessionChecker;
    private SessionParser sessionParser;

    @Autowired
    public DateHelperDefiner(RruleDefiner rruleDefiner,
                             SessionChecker sessionChecker, SessionParser sessionParser) {
        this.rruleDefiner = rruleDefiner;
        this.sessionChecker = sessionChecker;
        this.sessionParser = sessionParser;
    }

    public DateHelper defineDateHelper(List<TimeSlot> timeSlots) {
        DateHelper dateHelper;
        List<Session> sessions = sessionParser.timeSlotListToSessionList(timeSlots);
        if (sessionChecker.doAllSessionsTheSame(sessions)) {
            Rrule rrule = rruleDefiner.defineRrule(sessions);
            dateHelper = new RecurrenceDateHelperBuilder()
                    .setRrule(rrule)
                    .build();
            Frequency frequency = rrule.getFrequency();
            if (frequency.equals(Frequency.MINUTELY) || frequency.equals(Frequency.HOURLY)) {
                dateHelper = new ComplexDateHelperBuilder()
                        .setSessionList(sessions)
                        .build();
            }
        } else {
            dateHelper = new ComplexDateHelperBuilder()
                    .setSessionList(sessions)
                    .build();
        }
        return dateHelper;
    }
}