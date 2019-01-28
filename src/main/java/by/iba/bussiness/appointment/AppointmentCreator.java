package by.iba.bussiness.appointment;

import by.iba.bussiness.calendar.session.SessionParser;
import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
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

    public Appointment createAppointment(Meeting meeting, InvitationTemplate invitationTemplate) {
        Appointment appointment = new Appointment();
        appointment.setMeetingId(meeting.getId());
        appointment.setDescription(meeting.getDescription());
        appointment.setLocation(invitationTemplate.getLocationILT());
        appointment.setLocationInfo(meeting.getLocationInfo());
        appointment.setSummary(meeting.getSummary());
        appointment.setPlainDescription(meeting.getPlainDescription());
        appointment.setSubject(invitationTemplate.getSubject());
        appointment.setTitle(meeting.getTitle());
        appointment.setStartDateTime(meeting.getStartDateTime());
        appointment.setEndDateTime(meeting.getEndDateTime());

        List<TimeSlot> timeSlots = meeting.getTimeSlots();
        appointment.setSessionList(sessionParser.timeSlotListToSessionList(timeSlots));
        appointment.setDuration(meeting.getDuration());
        appointment.setFrom(invitationTemplate.getFrom());
        appointment.setFromName(invitationTemplate.getFromName());
        appointment.setTimeZone(meeting.getTimeZone());
        return appointment;
    }
}
