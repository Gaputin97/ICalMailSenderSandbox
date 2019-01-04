package by.iba.bussiness.appointment;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.invitation_template.service.InvitationTemplateService;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;

public class AppointmentCreator {
    MeetingService meetingService;
    InvitationTemplateService invitationTemplateService;

    @Autowired
    public AppointmentCreator(MeetingService meetingService, InvitationTemplateService invitationTemplateService) {
        this.meetingService = meetingService;
        this.invitationTemplateService = invitationTemplateService;
    }

    private Appointment createAppointmentForComparision(Meeting meeting, InvitationTemplate  invitationTemplate) {
        Appointment appointment = new Appointment();
        appointment.setSubject();
    }
}
