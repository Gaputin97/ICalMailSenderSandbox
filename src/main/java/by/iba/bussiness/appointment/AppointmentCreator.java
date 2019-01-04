package by.iba.bussiness.appointment;

import by.iba.bussiness.invitation_template.InvitationTemplate;
import by.iba.bussiness.meeting.Meeting;

public class AppointmentCreator {
    private Appointment createAppointment(Meeting meeting, InvitationTemplate  invitationTemplate) {
        Appointment appointment = new Appointment();
        appointment.setMeetingId(meeting.getId());
        appointment.setInvitationTemplateKey(invitationTemplate.getKey());

        //Description - is face to face description
        appointment.setDescription(invitationTemplate.getFaceToFaceDescription());
        appointment.setBlendedDescription(invitationTemplate.getBlendedDescription());
        appointment.setOnlineDescription(invitationTemplate.getOnlineDescription());
        appointment.setFaceToFaceDescription(invitationTemplate.getFaceToFaceDescription());

        //Location - is ILT description
        appointment.setLocation(invitationTemplate.getLocationILT());
        appointment.setLocationInfo(meeting.getLocationInfo());
        appointment.setLocationBLD(invitationTemplate.getLocationBLD());
        appointment.setLocationILT(invitationTemplate.getLocationILT());
        appointment.setLocationLVC(invitationTemplate.getLocationLVC());

        appointment.setSubject(invitationTemplate.getSubject());
        appointment.setTitle(meeting.getTitle());
        appointment.setStartDateTime(meeting.getStartDateTime());
        appointment.setEndDateTime(meeting.getEndDateTime());
        appointment.setTimeSlots(meeting.getTimeSlots());
        appointment.setDuration(meeting.getDuration());

        appointment.setOwner(meeting.getOwner());
        appointment.setTimeZone(meeting.getTimeZone());
        return appointment;
    }
}
