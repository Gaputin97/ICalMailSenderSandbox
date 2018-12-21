package by.iba.bussines.meeting.wrapper.definer;

import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.service.v1.MeetingServiceImpl;
import by.iba.bussines.meeting.wrapper.builder.complex.ComplexMeetingWrapperBuilder;
import by.iba.bussines.meeting.wrapper.builder.reccurence.RecurrenceMeetingWrapperBuilder;
import by.iba.bussines.meeting.wrapper.builder.single.SingleMeetingWrapperBuilder;
import by.iba.bussines.meeting.wrapper.constants.MeetingWrapperConstants;
import by.iba.bussines.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussines.rrule.definer.RruleDefiner;
import by.iba.bussines.rrule.model.Rrule;
import by.iba.bussines.session.checker.SessionChecker;
import by.iba.bussines.session.model.Session;
import by.iba.bussines.session.parser.SessionParser;
import by.iba.bussines.timeslot.model.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeetingWrapperDefiner {
    private MeetingServiceImpl meetingService;
    private MeetingWrapperConstants meetingWrapperConstants;
    private SessionParser sessionParser;
    private RruleDefiner rruleDefiner;
    private SessionChecker sessionChecker;
    private ComplexMeetingWrapperBuilder complexMeetingWrapperBuilder;
    private RecurrenceMeetingWrapperBuilder recurrenceMeetingWrapperBuilder;
    private SingleMeetingWrapperBuilder singleMeetingWrapperBuilder;

    @Autowired
    public MeetingWrapperDefiner(MeetingServiceImpl meetingService,
                                 MeetingWrapperConstants meetingWrapperConstants,
                                 SessionParser sessionParser,
                                 RruleDefiner rruleDefiner,
                                 SessionChecker sessionChecker
                                 /*ComplexMeetingWrapperBuilder complexMeetingWrapperBuilder,
                                 RecurrenceMeetingWrapperBuilder recurrenceMeetingWrapperBuilder,
                                 SingleMeetingWrapperBuilder singleMeetingWrapperBuilder*/) {
        this.meetingService = meetingService;
        this.meetingWrapperConstants = meetingWrapperConstants;
        this.sessionParser = sessionParser;
        this.rruleDefiner = rruleDefiner;
        this.sessionChecker = sessionChecker;
//        this.complexMeetingWrapperBuilder = complexMeetingWrapperBuilder;
//        this.recurrenceMeetingWrapperBuilder = recurrenceMeetingWrapperBuilder;
//        this.singleMeetingWrapperBuilder = singleMeetingWrapperBuilder;
    }

    public <T extends MeetingWrapper> T defineMeetingWrapper(Meeting meeting) {
        MeetingWrapper meetingWrapper;
        int amountOfTimeSlots = meeting.getTimeSlots().size();
        if (amountOfTimeSlots == meetingWrapperConstants.getAmountOfSessionsForSingleEvent()) {
            TimeSlot meetingTimeSlot = meeting.getTimeSlots().get(meetingWrapperConstants.getNumberOfFirstTimeSlot());
            Session meetingSession = sessionParser.timeSlotToSession(meetingTimeSlot);
            meetingWrapper = new SingleMeetingWrapperBuilder(singleMeetingWrapperBuilder)
                    .setSession(meetingSession)
                    .build();
        } else {
            List<Session> sessions = sessionParser.timeSlotListToSessionList(meeting.getTimeSlots());
            if (sessionChecker.doAllSessionsTheSame(meeting)) {
                Rrule rrule = rruleDefiner.defineRrule(sessions);
                meetingWrapper = new RecurrenceMeetingWrapperBuilder(recurrenceMeetingWrapperBuilder)
                        .setRrule(rrule)
                        .build();
            } else {
                meetingWrapper = new ComplexMeetingWrapperBuilder(complexMeetingWrapperBuilder)
                        .setSessions(sessions)
                        .build();
            }
        }
        return (T) meetingWrapper;
    }
}
