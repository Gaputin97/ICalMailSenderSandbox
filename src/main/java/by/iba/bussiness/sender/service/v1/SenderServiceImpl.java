package by.iba.bussiness.sender.service.v1;

import by.iba.bussiness.calendar.attendee.Attendee;
import by.iba.bussiness.calendar.creator.CalendarAttendeesInstaller;
import by.iba.bussiness.calendar.creator.CalendarTextEditor;
import by.iba.bussiness.calendar.date.DateHelperDefiner;
import by.iba.bussiness.calendar.CalendarFactory;
import by.iba.bussiness.enrollment.EnrollmentChecker;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.invitation_template.service.InvitationTemplateService;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import by.iba.bussiness.calendar.date.model.DateHelper;
import by.iba.bussiness.sender.MessageSender;
import by.iba.bussiness.sender.service.SenderService;
import by.iba.bussiness.response.CalendarSendingResponse;
import by.iba.exception.ServiceException;
import net.fortuna.ical4j.model.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class SenderServiceImpl implements SenderService {
    private static final Logger logger = LoggerFactory.getLogger(SenderServiceImpl.class);
    private MeetingService meetingService;
    private DateHelperDefiner dateHelperDefiner;
    private CalendarFactory calendarFactory;
    private CalendarAttendeesInstaller calendarAttendeesInstaller;
    private MessageSender messageSender;
    private EnrollmentChecker enrollmentChecker;
    private InvitationTemplateService invitationTemplateService;

    @Autowired
    public SenderServiceImpl(MeetingService meetingService,
                             DateHelperDefiner dateHelperDefiner,
                             CalendarFactory calendarFactory,
                             CalendarAttendeesInstaller calendarAttendeesInstaller,
                             MessageSender messageSender,
                             EnrollmentChecker enrollmentChecker,
                             InvitationTemplateService invitationTemplateService) {
        this.meetingService = meetingService;
        this.dateHelperDefiner = dateHelperDefiner;
        this.calendarFactory = calendarFactory;
        this.calendarAttendeesInstaller = calendarAttendeesInstaller;
        this.messageSender = messageSender;
        this.enrollmentChecker = enrollmentChecker;
        this.invitationTemplateService = invitationTemplateService;
    }

    @Override
    public CalendarSendingResponse sendMeeting(HttpServletRequest request, String meetingId, List<Attendee> attendees) {
        Meeting meeting = meetingService.getMeetingById(request, meetingId);
        String invitationTemplateKey = meeting.getInvitationTemplate();
        InvitationTemplate invitationTemplate = invitationTemplateService.getInvitationTemplateByCode(request, invitationTemplateKey);
        if (invitationTemplate == null) {
            logger.error("Invitation template of meeting " + meetingId + " is null");
            throw new ServiceException("Meeting " + meetingId + " doesn't have learner template");
        }
        List<String> emails = new ArrayList<>(attendees.size());
        attendees.forEach(x -> emails.add(x.getEmail()));
        enrollmentChecker.isExistsEnrollment(request, emails, meeting);
        DateHelper dateHelper = dateHelperDefiner.definerDateHelper(meeting);
        Calendar calendar = calendarFactory.createInvitationCalendarTemplate(dateHelper, meeting);
        List<Calendar> calendarList = calendarAttendeesInstaller.createCalendarList(emails, calendar);
        return messageSender.sendMessageToAllRecipients(calendarList, meeting);
    }
}
