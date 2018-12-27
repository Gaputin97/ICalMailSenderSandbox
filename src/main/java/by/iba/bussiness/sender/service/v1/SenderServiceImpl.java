package by.iba.bussiness.sender.service.v1;

import by.iba.bussiness.calendar.attendee.model.Attendee;
import by.iba.bussiness.calendar.creator.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.date.DateHelperDefiner;
import by.iba.bussiness.calendar.CalendarFactory;
import by.iba.bussiness.enrollment.EnrollmentChecker;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.sender.MessageSender;
import by.iba.bussiness.sender.service.SenderService;
import by.iba.bussiness.status.send.CalendarResponseStatus;
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
    private CalendarAttendeesInstaller calendarAttendeesInstaller;
    private MessageSender messageSender;
    private EnrollmentChecker enrollmentChecker;

    @Autowired
    public SenderServiceImpl(MeetingService meetingService,
                             DateHelperDefiner dateHelperDefiner,
                             CalendarFactory calendarFactory,
                             CalendarAttendeesInstaller calendarAttendeesInstaller,
                             MessageSender messageSender,
                             EnrollmentChecker enrollmentChecker) {
        this.meetingService = meetingService;
        this.dateHelperDefiner = dateHelperDefiner;
        this.calendarFactory = calendarFactory;
        this.calendarAttendeesInstaller = calendarAttendeesInstaller;
        this.messageSender = messageSender;
        this.enrollmentChecker = enrollmentChecker;
    }

    @Override
    public CalendarResponseStatus sendMeeting(HttpServletRequest request, String meetingId, List<Attendee> attendees) {
        List<String> emails = new ArrayList<>(attendees.size());
        attendees.forEach(x -> emails.add(x.getEmail()));
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        enrollmentChecker.isExistsEnrollment(request, emails, meeting);
        DateHelper dateHelper = dateHelperDefiner.definerDateHelper(meeting);
        Calendar calendar = calendarFactory.createInvitationCalendarTemplate(dateHelper, meeting);
        //      List<Calendar> calendarList = calendarListCreator.createCalendarList(emails, calendar);
//        if(dateHelper.getMeetingType().equals(MeetingType.SINGLE) || dateHelper.getMeetingType().equals(MeetingType.RECURRENCE)) {
//            messageSender.sendMessageToAllRecipients(calendarList, meeting);
//        } else if (dateHelper.getMeetingType().equals(MeetingType.COMPLEX)) {
//
//        }
        List<Calendar> calendarList = calendarAttendeesInstaller.createCalendarList(emails, calendar);
        return messageSender.sendMessageToAllRecipients(calendarList, meeting);
    }
}
