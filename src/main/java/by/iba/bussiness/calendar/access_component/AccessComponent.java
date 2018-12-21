package by.iba.bussines.calendar.access_component;

import by.iba.bussines.calendar.attendee.Attendee;
import by.iba.bussines.calendar.creator.CalendarListCreator;
import by.iba.bussines.calendar.factory.CalendarFactory;
import by.iba.bussines.meeting.model.Meeting;
import by.iba.bussines.meeting.service.MeetingService;
import by.iba.bussines.meeting.wrapper.definer.MeetingWrapperDefiner;
import by.iba.bussines.meeting.wrapper.model.MeetingWrapper;
import by.iba.bussines.sender.service.manager.MultiplyCalendarSender;
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
