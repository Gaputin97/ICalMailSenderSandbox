package by.iba.bussiness.sender.service.v1;

import by.iba.bussiness.calendar.attendee.model.Attendee;
import by.iba.bussiness.calendar.creator.CalendarListCreator;
import by.iba.bussiness.calendar.date.definer.DateHelperDefiner;
import by.iba.bussiness.calendar.factory.CalendarFactory;
import by.iba.bussiness.enrollment.checker.EnrollmentChecker;
import by.iba.bussiness.meeting.model.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.meeting.type.MeetingType;
import by.iba.bussiness.sender.MessageSender;
import by.iba.bussiness.sender.service.SenderService;
import by.iba.bussiness.status.send.CalendarSendingStatus;
import net.fortuna.ical4j.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class SenderServiceImpl implements SenderService {
    private MeetingService meetingService;
    private DateHelperDefiner dateHelperDefiner;
    private CalendarFactory calendarFactory;
    private CalendarListCreator calendarListCreator;
    private MessageSender messageSender;
    private EnrollmentChecker enrollmentChecker;

    @Autowired
    public SenderServiceImpl(MeetingService meetingService,
                             DateHelperDefiner dateHelperDefiner,
                             CalendarFactory calendarFactory,
                             CalendarListCreator calendarListCreator,
                             MessageSender messageSender,
                             EnrollmentChecker enrollmentChecker) {
        this.meetingService = meetingService;
        this.dateHelperDefiner = dateHelperDefiner;
        this.calendarFactory = calendarFactory;
        this.calendarListCreator = calendarListCreator;
        this.messageSender = messageSender;
        this.enrollmentChecker = enrollmentChecker;
    }

    @Override
    public CalendarSendingStatus sendMeeting(HttpServletRequest request, String meetingId, List<Attendee> attendees) {
        List<String> emails = new ArrayList<>(attendees.size());
        attendees.forEach(x -> emails.add(x.getEmail()));
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        enrollmentChecker.isExistsEnrollment(request, emails, meeting);
        DateHelper dateHelper = dateHelperDefiner.definerDateHelper(meeting);
        Calendar calendar = calendarFactory.createInvitationCalendarTemplate(dateHelper, meeting);
<<<<<<< HEAD
        List<Calendar> calendarList = calendarListCreator.createCalendarList(emailList, calendar);
        if(dateHelper.getMeetingType().equals(MeetingType.SINGLE) || dateHelper.getMeetingType().equals(MeetingType.RECURRENCE)) {
            messageSender.sendMessageToAllRecipients(calendarList);
        } else if (dateHelper.getMeetingType().equals(MeetingType.COMPLEX)) {
            
        }
=======
        List<Calendar> calendarList = calendarListCreator.createCalendarList(emails, calendar);
        return messageSender.sendMessageToAllRecipients(calendarList, meeting);
>>>>>>> dev1
    }
}
