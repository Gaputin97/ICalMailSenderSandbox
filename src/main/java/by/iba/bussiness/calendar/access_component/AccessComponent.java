package by.iba.bussiness.calendar.access_component;

import by.iba.bussiness.calendar.creator.CalendarListCreator;
import by.iba.bussiness.calendar.factory.CalendarFactory;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.meeting.wrapper.definer.MeetingWrapperDefiner;
import by.iba.bussiness.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussiness.sender.service.manager.MultiplyCalendarSender;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class AccessComponent {
    private MeetingService meetingService;
    private MeetingWrapperDefiner meetingWrapperDefiner;
    private CalendarFactory calendarFactory;
    private CalendarListCreator calendarListCreator;
    private MultiplyCalendarSender multiplyCalendarSender;

    @Autowired
    public AccessComponent(MeetingService meetingService,
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

    public void getMeeting(HttpServletRequest request, String meetingId, String attendeeList){
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        MeetingWrapper meetingWrapper = meetingWrapperDefiner.defineMeetingWrapper(meeting);
        Calendar calendar = calendarFactory.createInvitationCalendarTemplate(meetingWrapper, request, meeting);
        List<Calendar> calendarList = calendarListCreator.createCalendarList(attendeeList, calendar);
        multiplyCalendarSender.sendToEndUser(calendarList);
    }
}
