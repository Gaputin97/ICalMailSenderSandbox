package by.iba.bussiness.calendar.date;

import by.iba.bussiness.calendar.date.builder.ComplexDateHelperBuilder;
import by.iba.bussiness.calendar.date.builder.RecurrenceDateHelperBuilder;
import by.iba.bussiness.calendar.date.builder.SimpleDateHelperBuilder;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.calendar.rrule.definer.RruleDefiner;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.session.SessionChecker;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DateHelperDefiner {
    private static final  Logger logger = LoggerFactory.getLogger(DateHelperDefiner.class);
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

    public DateHelper definerDateHelper(Meeting meeting) {
        DateHelper dateHelper;
        int amountOfTimeSlots = meeting.getTimeSlots().size();
        if (amountOfTimeSlots == DateHelperConstants.AMOUNT_OF_SESSIONS_FOR_SINGLE_EVENT) {
            TimeSlot meetingTimeSlot = meeting.getTimeSlots().get(DateHelperConstants.NUMBER_OF_FIRST_TIME_SLOT);
            Session meetingSession = sessionParser.timeSlotToSession(meetingTimeSlot);
            SimpleDateHelperBuilder simpleDateHelperBuilder = new SimpleDateHelperBuilder();
            dateHelper = simpleDateHelperBuilder
                    .setSession(meetingSession)
                    .build();
            logger.debug("Meeting type of meeting with id " + meeting.getId() + " is simple");
        } else {
            List<Session> sessions = sessionParser.timeSlotListToSessionList(meeting.getTimeSlots());
            if (sessionChecker.doAllSessionsTheSame(meeting)) {
                Rrule rrule = rruleDefiner.defineRrule(sessions);
                RecurrenceDateHelperBuilder recurrenceDateHelperBuilder = new RecurrenceDateHelperBuilder();
                dateHelper = recurrenceDateHelperBuilder
                        .setRrule(rrule)
                        .build();
                logger.debug("Meeting type of meeting with id " + meeting.getId() + " is recurrence");
            } else {
                ComplexDateHelperBuilder complexDateHelperBuilder = new ComplexDateHelperBuilder();
                dateHelper = complexDateHelperBuilder
                        .setSessionList(sessions)
                        .build();
                logger.debug("Meeting type of meeting with id " + meeting.getId() + " is complex");
            }
        }
        return dateHelper;
    }
}

