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

    public Appointment createAppointmentWithMainFields(Meeting newMeeting, InvitationTemplate newInvitationTemplate) {
        Appointment newAppointment = new Appointment();
        newAppointment.setMeetingId(newMeeting.getId());

        newAppointment.setLocation(newInvitationTemplate.getLocationILT());
        newAppointment.setLocationInfo(newMeeting.getLocationInfo());
        newAppointment.setTimeZone(newMeeting.getTimeZone());

        newAppointment.setSummary(newMeeting.getSummary());
        newAppointment.setSubject(newInvitationTemplate.getSubject());
        newAppointment.setTitle(newMeeting.getTitle());

        newAppointment.setDescription(newMeeting.getDescription());
        newAppointment.setPlainDescription(newMeeting.getPlainDescription());

        newAppointment.setStartDateTime(newMeeting.getStartDateTime());
        newAppointment.setEndDateTime(newMeeting.getEndDateTime());
        newAppointment.setDuration(newMeeting.getDuration());

        List<TimeSlot> timeSlots = newMeeting.getTimeSlots();
        newAppointment.setSessionList(sessionParser.timeSlotListToSessionList(timeSlots));

        newAppointment.setFrom(newInvitationTemplate.getFrom());
        newAppointment.setFromName(newInvitationTemplate.getFromName());
        return newAppointment;
    }
}
