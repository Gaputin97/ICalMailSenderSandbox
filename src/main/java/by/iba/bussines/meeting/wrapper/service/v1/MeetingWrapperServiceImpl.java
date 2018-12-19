package by.iba.bussines.meeting.wrapper.service.v1;

import by.iba.bussines.calendar.factory.type.MeetingType;
import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.service.v1.MeetingServiceImpl;
import by.iba.bussines.meeting.wrapper.builder.AbstractMeetingWrapperBuilder;
import by.iba.bussines.meeting.wrapper.builder.complex.ComplexMeetingWrapperBuilder;
import by.iba.bussines.meeting.wrapper.constants.MeetingWrapperConstants;
import by.iba.bussines.meeting.wrapper.model.AbstractMeetingWrapper;
import by.iba.bussines.meeting.wrapper.model.complex.ComplexMeetingWrapper;
import by.iba.bussines.meeting.wrapper.model.reccurence.ReccurenceMeetingWrapper;
import by.iba.bussines.meeting.wrapper.model.single.SingleMeetingWrapper;
import by.iba.bussines.meeting.wrapper.service.MeetingWrapperService;
import by.iba.bussines.rrule.definer.RruleDefiner;
import by.iba.bussines.rrule.model.Rrule;
import by.iba.bussines.session.checker.SessionChecker;
import by.iba.bussines.session.model.Session;
import by.iba.bussines.session.parser.SessionParser;
import by.iba.bussines.session.service.v1.SessionServiceImpl;
import by.iba.bussines.timeslot.model.TimeSlot;
import by.iba.bussines.timeslot.service.v1.TimeSlotServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MeetingWrapperServiceImpl implements MeetingWrapperService {

    private MeetingServiceImpl meetingService;
    private MeetingWrapperConstants meetingWrapperConstants;
    private SessionParser sessionParser;
    private RruleDefiner rruleDefiner;
    private SessionChecker sessionChecker;
    private AbstractMeetingWrapperBuilder complexMeetingWrapperBuilder;
    private TimeSlotServiceImpl timeSlotService;
    private SessionServiceImpl sessionService;

    @Autowired
    public MeetingWrapperServiceImpl(MeetingServiceImpl meetingService,
                                     MeetingWrapperConstants meetingWrapperConstants,
                                     SessionParser sessionParser,
                                     RruleDefiner rruleDefiner,
                                     SessionChecker sessionChecker, ComplexMeetingWrapperBuilder complexMeetingWrapperBuilder) {
        this.meetingService = meetingService;
        this.meetingWrapperConstants = meetingWrapperConstants;
        this.sessionParser = sessionParser;
        this.rruleDefiner = rruleDefiner;
        this.sessionChecker = sessionChecker;
        this.complexMeetingWrapperBuilder = complexMeetingWrapperBuilder;
    }

    @Override
    public AbstractMeetingWrapper defineMeetingWrapper(HttpServletRequest request, String meetingId, List<String> recipients) {
        AbstractMeetingWrapper meetingWrapper;
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        int amountOfTimeSlots = meeting.getTimeSlots().size();
        if (amountOfTimeSlots == meetingWrapperConstants.getAmountOfSessionsForSingleEvent()) {
            TimeSlot meetingTimeSlot = meeting.getTimeSlots().get(meetingWrapperConstants.getNumberOfFirstTimeSlot());
            Session meetingSession = sessionParser.timeSlotToSession(meetingTimeSlot);
            meetingWrapper = new SingleMeetingWrapper();
            meetingWrapper.setMeetingType(MeetingType.SIMPLE);
            meetingWrapper.setMeetingId(meetingId);
            meetingWrapper.setRecipients(recipients);
            complexMeetingWrapperBuilder.setMeetingId(meetingId);
            ((SingleMeetingWrapper) meetingWrapper).setSession(meetingSession);
        } else {
            List<Session> sessions = sessionParser.timeSlotListToSessionList(meeting.getTimeSlots());
            if (sessionChecker.doAllSessionsTheSame(meeting)) {
                Rrule rrule = rruleDefiner.defineRrule(sessions);
                meetingWrapper = new ReccurenceMeetingWrapper();
                meetingWrapper.setRecipients(recipients);
                meetingWrapper.setMeetingId(meetingId);
                meetingWrapper.setMeetingType(MeetingType.RECURRENCE);
                ((ReccurenceMeetingWrapper) meetingWrapper).setRrule(rrule);
            } else {
                meetingWrapper = new ComplexMeetingWrapper();
                meetingWrapper.setRecipients(recipients);
                meetingWrapper.setMeetingId(meetingId);
                meetingWrapper.setMeetingType(MeetingType.COMPLEX);
                ((ComplexMeetingWrapper) meetingWrapper).setSessions(sessions);
            }
        }
        return meetingWrapper;

    }

}
