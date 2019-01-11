package by.iba.bussiness.calendar.date;

import by.iba.bussiness.calendar.creator.complex.ComplexMeetingCalendarTemplateCreator;
import by.iba.bussiness.calendar.date.builder.ComplexDateHelperBuilder;
import by.iba.bussiness.calendar.date.builder.RecurrenceDateHelperBuilder;
import by.iba.bussiness.calendar.date.builder.SimpleDateHelperBuilder;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.calendar.rrule.Rrule;
import by.iba.bussiness.calendar.rrule.definer.RruleDefiner;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionChecker;
import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.exception.CalendarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DateHelperDefiner {
    private static final Logger logger = LoggerFactory.getLogger(DateHelperDefiner.class);
    private static final int NUMBER_OF_THE_FIRST_TIME_SLOT = 0;
    private static final int AMOUNT_OF_SESSIONS_FOR_SINGLE_EVENT = 1;

    private SessionParser sessionParser;

    @Autowired
    public DateHelperDefiner(SessionParser sessionParser,
                             RruleDefiner rruleDefiner,
                             SessionChecker sessionChecker) {
        this.sessionParser = sessionParser;
    }

    public DateHelper defineDateHelper(List<TimeSlot> timeSlots) {
        DateHelper dateHelper;
        int amountOfTimeSlots = timeSlots.size();
        if (amountOfTimeSlots == AMOUNT_OF_SESSIONS_FOR_SINGLE_EVENT) {
            TimeSlot meetingTimeSlot = timeSlots.get(NUMBER_OF_THE_FIRST_TIME_SLOT);
            Session meetingSession = sessionParser.timeSlotToSession(meetingTimeSlot);
            SimpleDateHelperBuilder simpleDateHelperBuilder = new SimpleDateHelperBuilder();
            dateHelper = simpleDateHelperBuilder
                    .setSession(meetingSession)
                    .build();
        } else {
            logger.error("Timeslots from this meeting can't be transform to simple event");
            throw new CalendarException("Timeslots from this meeting can't be transform to simple event");
        }
        return dateHelper;
    }
}

