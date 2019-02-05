package by.iba.bussiness.appointment;

import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.invitation_template.body.InvitationTemplateBodyPart;
import by.iba.bussiness.invitation_template.body.InvitationTemplateBodyPartDefiner;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.bussiness.meeting.type.MeetingLocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentCreator {
    private SessionParser sessionParser;
    private InvitationTemplateBodyPartDefiner invitationTemplateBodyPartDefiner;

    @Autowired
    public AppointmentCreator(SessionParser sessionParser,
                              InvitationTemplateBodyPartDefiner invitationTemplateBodyPartDefiner) {
        this.sessionParser = sessionParser;
        this.invitationTemplateBodyPartDefiner = invitationTemplateBodyPartDefiner;
    }

    public Appointment createAppointment(Meeting meeting, InvitationTemplate invitationTemplate) {
        Appointment newAppointment = new Appointment();
        MeetingLocationType meetingLocationType = MeetingLocationType.valueOf(meeting.getType());
        InvitationTemplateBodyPart invitationTemplateBodyPart = invitationTemplateBodyPartDefiner.defineBodyPart(invitationTemplate, meetingLocationType);
        String location = invitationTemplateBodyPart.getLocation();
        String description = invitationTemplateBodyPart.getDescription();
        String plainDescription = invitationTemplateBodyPart.getPlainDescription();
        newAppointment.setMeetingId(meeting.getId());
        newAppointment.setDescription(description);
        newAppointment.setLocation(location);
        newAppointment.setPlainDescription(plainDescription);
        newAppointment.setSubject(invitationTemplate.getSubject());
        // TODO
        newAppointment.setTitle(meeting.getTitle());
        newAppointment.setPlainDescription(meeting.getPlainDescription());

        List<TimeSlot> timeSlots = meeting.getTimeSlots();
        newAppointment.setSessionList(sessionParser.timeSlotListToSessionList(timeSlots));

        return newAppointment;
    }
}
