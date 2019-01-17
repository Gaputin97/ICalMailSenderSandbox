package by.iba.bussiness.calendar.date.helper;

import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.rrule.frequence.FrequencyDefiner;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionChecker;
import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.meeting.MeetingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeetingTypeDefiner {
    private static final Logger logger = LoggerFactory.getLogger(MeetingTypeDefiner.class);
    private SessionParser sessionParser;
    private FrequencyDefiner frequencyDefiner;
    private SessionChecker sessionChecker;

    @Autowired
    public MeetingTypeDefiner(SessionParser sessionParser,
                              FrequencyDefiner frequencyDefiner,
                              SessionChecker sessionChecker) {
        this.sessionParser = sessionParser;
        this.frequencyDefiner = frequencyDefiner;
        this.sessionChecker = sessionChecker;
    }

    public MeetingType defineMeetingType(List<Session> sessionList) {
        MeetingType meetingType;
        List<Date> startDatesOfSessions = sessionList.stream().map(Session::getStartDateTime).collect(Collectors.toList());
        if (sessionChecker.doAllSessionsTheSame(sessionList)) {
            Frequency frequency = frequencyDefiner.defineFrequence(startDatesOfSessions);
            meetingType = MeetingType.SIMPLE;
            if (frequency.equals(Frequency.MINUTELY) || frequency.equals(Frequency.HOURLY)) {
                meetingType = MeetingType.COMPLEX;
            }
        } else {
            meetingType = MeetingType.COMPLEX;
        }
        return meetingType;
    }

}