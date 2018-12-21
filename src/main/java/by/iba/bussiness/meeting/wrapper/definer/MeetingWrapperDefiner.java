package by.iba.bussiness.meeting.wrapper.definer;

import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.service.v1.MeetingServiceImpl;
import by.iba.bussiness.meeting.wrapper.builder.complex.ComplexMeetingWrapperBuilder;
import by.iba.bussiness.meeting.wrapper.builder.reccurence.RecurrenceMeetingWrapperBuilder;
import by.iba.bussiness.meeting.wrapper.builder.single.SingleMeetingWrapperBuilder;
import by.iba.bussiness.meeting.wrapper.constants.MeetingWrapperConstants;
import by.iba.bussiness.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussiness.rrule.definer.RruleDefiner;
import by.iba.bussiness.rrule.model.Rrule;
import by.iba.bussiness.session.checker.SessionChecker;
import by.iba.bussiness.session.model.Session;
import by.iba.bussiness.session.parser.SessionParser;
import by.iba.bussiness.timeslot.model.TimeSlot;
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
<<<<<<< HEAD:src/main/java/by/iba/bussines/meeting/wrapper/definer/MeetingWrapperDefiner.java
                                 SessionChecker sessionChecker,
                                 ComplexMeetingWrapperBuilder complexMeetingWrapperBuilder,
                                 RecurrenceMeetingWrapperBuilder recurrenceMeetingWrapperBuilder,
                                 SingleMeetingWrapperBuilder singleMeetingWrapperBuilder) {
=======
                                 SessionChecker sessionChecker
                                 /*ComplexMeetingWrapperBuilder complexMeetingWrapperBuilder,
                                 RecurrenceMeetingWrapperBuilder recurrenceMeetingWrapperBuilder,
                                 SingleMeetingWrapperBuilder singleMeetingWrapperBuilder*/) {
>>>>>>> 98c346483b3f8847b2afbd934c2bf7742e20ed68:src/main/java/by/iba/bussiness/meeting/wrapper/definer/MeetingWrapperDefiner.java
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
