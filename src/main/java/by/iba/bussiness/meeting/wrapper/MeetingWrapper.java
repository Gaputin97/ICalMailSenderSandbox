package by.iba.bussiness.meeting.wrapper;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;

public class MeetingWrapper {
    private Meeting meeting;
    private String calendarUid;
    private InvitationTemplate invitationTemplate;

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public String getCalendarUid() {
        return calendarUid;
    }

    public void setCalendarUid(String calendarUid) {
        this.calendarUid = calendarUid;
    }

    public InvitationTemplate getInvitationTemplate() {
        return invitationTemplate;
    }

    public void setInvitationTemplate(InvitationTemplate invitationTemplate) {
        this.invitationTemplate = invitationTemplate;
    }
}
