package by.iba.bussiness.calendar.date;

import by.iba.bussiness.calendar.date.builder.ComplexDateHelperBuilder;
import by.iba.bussiness.calendar.date.builder.RecurrenceDateHelperBuilder;
import by.iba.bussiness.calendar.date.builder.SimpleDateHelperBuilder;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.v1.MeetingServiceImpl;
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
    private final static Logger logger = LoggerFactory.getLogger(DateHelperDefiner.class);
    private MeetingServiceImpl meetingService;
    private DateHelperConstants dateHelperConstants;
    private SessionParser sessionParser;
    private RruleDefiner rruleDefiner;
    private SessionChecker sessionChecker;
    private ComplexDateHelperBuilder complexDateHelperBuilder;
    private RecurrenceDateHelperBuilder recurrenceDateHelperBuilder;
    private SimpleDateHelperBuilder simpleDateHelperBuilder;

    @Autowired
    public DateHelperDefiner(MeetingServiceImpl meetingService,
                             DateHelperConstants dateHelperConstants,
                             SessionParser sessionParser,
                             RruleDefiner rruleDefiner,
                             SessionChecker sessionChecker,
                             ComplexDateHelperBuilder complexDateHelperBuilder,
                             RecurrenceDateHelperBuilder recurrenceDateHelperBuilder,
                             SimpleDateHelperBuilder simpleDateHelperBuilder) {
        this.meetingService = meetingService;
        this.dateHelperConstants = dateHelperConstants;
        this.sessionParser = sessionParser;
        this.rruleDefiner = rruleDefiner;
        this.sessionChecker = sessionChecker;
        this.complexDateHelperBuilder = complexDateHelperBuilder;
        this.recurrenceDateHelperBuilder = recurrenceDateHelperBuilder;
        this.simpleDateHelperBuilder = simpleDateHelperBuilder;
    }

    public DateHelper definerDateHelper(Meeting meeting) {
        DateHelper dateHelper;
        int amountOfTimeSlots = meeting.getTimeSlots().size();
        if (amountOfTimeSlots == dateHelperConstants.getAmountOfSessionsForSingleEvent()) {
            TimeSlot meetingTimeSlot = meeting.getTimeSlots().get(dateHelperConstants.getNumberOfFirstTimeSlot());
            Session meetingSession = sessionParser.timeSlotToSession(meetingTimeSlot);
            dateHelper = simpleDateHelperBuilder
                    .setSession(meetingSession)
                    .build();
            logger.debug("Meeting type of meeting with id " + meeting.getId() + " is simple");
        } else {
            List<Session> sessions = sessionParser.timeSlotListToSessionList(meeting.getTimeSlots());
            if (sessionChecker.doAllSessionsTheSame(meeting)) {
                Rrule rrule = rruleDefiner.defineRrule(sessions);
                dateHelper = recurrenceDateHelperBuilder
                        .setRrule(rrule)
                        .build();
                logger.debug("Meeting type of meeting with id " + meeting.getId() + " is recurrence");
            } else {
                dateHelper = complexDateHelperBuilder
                        .setSessionList(sessions)
                        .build();
                logger.debug("Meeting type of meeting with id " + meeting.getId() + " is complex");
            }
        }
        return dateHelper;
    }
}

