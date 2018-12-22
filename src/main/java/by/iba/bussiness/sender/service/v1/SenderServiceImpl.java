package by.iba.bussiness.sender.service.v1;

import by.iba.bussiness.calendar.creator.CalendarListCreator;
import by.iba.bussiness.calendar.date.definer.DateHelperDefiner;
import by.iba.bussiness.calendar.email.Email;
import by.iba.bussiness.calendar.factory.CalendarFactory;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.sender.MessageSender;
import by.iba.bussiness.sender.service.SenderService;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class SenderServiceImpl implements SenderService {
    private MeetingService meetingService;
    private DateHelperDefiner dateHelperDefiner;
    private CalendarFactory calendarFactory;
    private CalendarListCreator calendarListCreator;
    private MessageSender messageSender;

    @Autowired
    public SenderServiceImpl(MeetingService meetingService,
                             DateHelperDefiner dateHelperDefiner,
                             CalendarFactory calendarFactory,
                             CalendarListCreator calendarListCreator,
                             MessageSender messageSender) {
        this.meetingService = meetingService;
        this.dateHelperDefiner = dateHelperDefiner;
        this.calendarFactory = calendarFactory;
        this.calendarListCreator = calendarListCreator;
        this.messageSender = messageSender;
    }

    @Override
    public void sendMeeting(HttpServletRequest request, String meetingId, Email emailList) {
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        DateHelper dateHelper = dateHelperDefiner.definerDateHelper(meeting);
        Calendar calendar = calendarFactory.createInvitationCalendarTemplate(dateHelper, meeting);
        List<Calendar> calendarList = calendarListCreator.createCalendarList(emailList, calendar);
        messageSender.sendMessageToAllRecipients(calendarList);
    }
}
