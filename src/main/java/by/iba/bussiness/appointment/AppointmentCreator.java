package by.iba.bussiness.appointment;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.timeslot.TimeSlot;
import by.iba.bussiness.meeting.timeslot.TimeSlotUidDefiner;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppointmentCreator {

    @Autowired
    private TimeSlotUidDefiner timeSlotUidDefiner;

    public Appointment createAppointment(Meeting meeting, InvitationTemplate invitationTemplate) {
        String meetingId = meeting.getId().toString();

        Appointment appointment = new Appointment();
        appointment.setMeetingId(meeting.getId());

        appointment.setDescription(invitationTemplate.getFaceToFaceDescription());
        appointment.setBlendedDescription(invitationTemplate.getBlendedDescription());
        appointment.setOnlineDescription(invitationTemplate.getOnlineDescription());
        appointment.setFaceToFaceDescription(invitationTemplate.getFaceToFaceDescription());

        appointment.setLocation(invitationTemplate.getLocationILT());
        appointment.setLocationInfo(meeting.getLocationInfo());
        appointment.setLocationBLD(invitationTemplate.getLocationBLD());
        appointment.setLocationILT(invitationTemplate.getLocationILT());
        appointment.setLocationLVC(invitationTemplate.getLocationLVC());

        appointment.setSummary(meeting.getSummary());

        appointment.setSubject(invitationTemplate.getSubject());
        appointment.setTitle(meeting.getTitle());
        appointment.setStartDateTime(meeting.getStartDateTime());
        appointment.setEndDateTime(meeting.getEndDateTime());

        List<TimeSlot> timeSlots = meeting.getTimeSlots();
        for (TimeSlot timeSlot : timeSlots) {
            String timeSlotId = Integer.toString(timeSlot.getId());
            timeSlot.setUuid(timeSlotUidDefiner.defineTimeSlotUid(meetingId, timeSlotId));
        }
        appointment.setTimeSlots(meeting.getTimeSlots());
        appointment.setDuration(meeting.getDuration());

        appointment.setOwner(meeting.getOwner());
        appointment.setTimeZone(meeting.getTimeZone());
        return appointment;
    }
}
