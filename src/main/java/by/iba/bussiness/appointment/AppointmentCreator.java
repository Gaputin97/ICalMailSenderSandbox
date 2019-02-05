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
        MeetingLocationType meetingLocationType = MeetingLocationType.valueOf(meeting.getType());
        InvitationTemplateBodyPart invitationTemplateBodyPart = invitationTemplateBodyPartDefiner.defineBodyPart(invitationTemplate, meetingLocationType);
        String location = invitationTemplateBodyPart.getLocation();
        String description = invitationTemplateBodyPart.getDescription();
        String plainDescription = invitationTemplateBodyPart.getPlainDescription();

        Appointment newAppointment = new Appointment();
        newAppointment.setFrom(invitationTemplate.getFrom());
        newAppointment.setFromName(invitationTemplate.getFromName());
        newAppointment.setSubject(invitationTemplate.getSubject());
        newAppointment.setDescription(description);
        newAppointment.setPlainDescription(plainDescription);
        newAppointment.setLocation(location);

        newAppointment.setStartDateTime(meeting.getStartDateTime());
        newAppointment.setEndDateTime(meeting.getEndDateTime());
        newAppointment.setTitle(meeting.getTitle());
        newAppointment.setMeetingId(meeting.getId());

        List<TimeSlot> timeSlots = meeting.getTimeSlots();
        newAppointment.setSessionList(sessionParser.timeSlotListToSessionList(timeSlots));

        return newAppointment;
    }
}
