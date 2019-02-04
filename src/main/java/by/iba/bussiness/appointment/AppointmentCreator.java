package by.iba.bussiness.appointment;

import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.bussiness.meeting.type.MeetingLocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentCreator {
    private SessionParser sessionParser;

    @Autowired
    public AppointmentCreator(SessionParser sessionParser) {
        this.sessionParser = sessionParser;
    }

    public Appointment createAppointmentWithMainFields(Meeting meeting, InvitationTemplate invitationTemplate) {
        Appointment newAppointment = new Appointment();
        newAppointment.setMeetingId(meeting.getId());
        String location = null;
        String description = null;
        MeetingLocationType meetingLocationType = MeetingLocationType.valueOf(meeting.getType());
        switch (meetingLocationType) {
            case ILT:
                location = invitationTemplate.getLocationILT();
                description = invitationTemplate.getFaceToFaceDescription();
                break;
            case CON:
                location = invitationTemplate.getLocationBLD();
                description = invitationTemplate.getBlendedDescription();
                break;
            case LVC:
                location = invitationTemplate.getLocationLVC();
                description = invitationTemplate.getOnlineDescription();
        }
        newAppointment.setDescription(description);
        newAppointment.setLocation(location);
        newAppointment.setSummary(meeting.getSummary());
        newAppointment.setSubject(invitationTemplate.getSubject());
        newAppointment.setTitle(meeting.getTitle());
        newAppointment.setPlainDescription(meeting.getPlainDescription());

        newAppointment.setStartDateTime(meeting.getStartDateTime());
        newAppointment.setEndDateTime(meeting.getEndDateTime());
        newAppointment.setDuration(meeting.getDuration());

        List<TimeSlot> timeSlots = meeting.getTimeSlots();
        newAppointment.setSessionList(sessionParser.timeSlotListToSessionList(timeSlots));

        return newAppointment;
    }
}
