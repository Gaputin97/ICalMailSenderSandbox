package by.iba.bussiness.sender.service.v1;

import by.iba.bussiness.calendar.creator.CalendarListCreator;
import by.iba.bussiness.calendar.email.Email;
import by.iba.bussiness.calendar.factory.CalendarFactory;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.meeting.wrapper.definer.MeetingWrapperDefiner;
import by.iba.bussiness.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussiness.sender.manager.MultiplyCalendarSender;
import by.iba.bussiness.sender.service.SenderService;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class SenderServiceImpl implements SenderService {
    private MeetingService meetingService;
    private MeetingWrapperDefiner meetingWrapperDefiner;
    private CalendarFactory calendarFactory;
    private CalendarListCreator calendarListCreator;
    private MultiplyCalendarSender multiplyCalendarSender;

    @Autowired
    public SenderServiceImpl(MeetingService meetingService,
                             MeetingWrapperDefiner meetingWrapperDefiner,
                             CalendarFactory calendarFactory,
                             CalendarListCreator calendarListCreator,
                             MultiplyCalendarSender multiplyCalendarSender) {
        this.meetingService = meetingService;
        this.meetingWrapperDefiner = meetingWrapperDefiner;
        this.calendarFactory = calendarFactory;
        this.calendarListCreator = calendarListCreator;
        this.multiplyCalendarSender = multiplyCalendarSender;
    }

    public void sendMeeting(HttpServletRequest request, String meetingId, Email emailList){
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        MeetingWrapper meetingWrapper = meetingWrapperDefiner.defineMeetingWrapper(meeting);
        Calendar calendar = calendarFactory.createInvitationCalendarTemplate(meetingWrapper, request, meeting);
        List<Calendar> calendarList = calendarListCreator.createCalendarList(emailList, calendar);
        multiplyCalendarSender.sendToEndUser(calendarList);
    }
}
