package by.iba.bussiness.meeting.type;

import by.iba.bussiness.calendar.rrule.frequence.Frequency;
import by.iba.bussiness.calendar.rrule.frequence.FrequencyDefiner;
import by.iba.bussiness.calendar.session.Session;
import by.iba.bussiness.calendar.session.SessionChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MeetingTypeDefiner {
    private static final Logger logger = LoggerFactory.getLogger(MeetingTypeDefiner.class);
    private FrequencyDefiner frequencyDefiner;
    private SessionChecker sessionChecker;

    @Autowired
    public MeetingTypeDefiner(FrequencyDefiner frequencyDefiner,
                              SessionChecker sessionChecker) {
        this.frequencyDefiner = frequencyDefiner;
        this.sessionChecker = sessionChecker;
    }

    public MeetingType defineMeetingType(List<Session> sessionList) {
        MeetingType meetingType;
        List<Instant> startDatesOfSessions = sessionList.stream().map(Session::getStartDateTime).collect(Collectors.toList());
        if (sessionChecker.areAllSessionsTheSame(sessionList)) {
            Frequency frequency = frequencyDefiner.defineFrequency(startDatesOfSessions);
            if (frequency.equals(Frequency.MINUTELY) || frequency.equals(Frequency.HOURLY)) {
                meetingType = MeetingType.COMPLEX;
            } else {
                meetingType = MeetingType.SIMPLE;
            }
        } else {
            meetingType = MeetingType.COMPLEX;
        }
        return meetingType;
    }
}