package by.iba.bussiness.appointment.comparator;

import by.iba.bussiness.appointment.repository.AppointmentRepository;
import by.iba.bussiness.invitation_template.service.InvitationTemplateService;
import by.iba.bussiness.meeting.Meeting;
import by.iba.bussiness.meeting.service.MeetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;


@Component
public class DataComparator {
    private static final Logger logger = LoggerFactory.getLogger(DataComparator.class);
    private MeetingService meetingService;
    private InvitationTemplateService invitationTemplateService;
    private AppointmentRepository appointmentRepository;

    @Autowired
    public DataComparator(MeetingService meetingService,
                          InvitationTemplateService invitationTemplateService,
                          AppointmentRepository appointmentRepository) {
        this.meetingService = meetingService;
        this.invitationTemplateService = invitationTemplateService;
        this.appointmentRepository = appointmentRepository;
    }

    public Update addUpdateValues(Meeting meeting) {
        Update update = new Update();
        update.set("description", meeting.getDescription());
        update.set("duration",meeting.getDuration());
        update.set("startDateTime", meeting.getStartDateTime());
        update.set("endDateTime", meeting.getEndDateTime());
        update.set("invitationTemplateKey", meeting.getInvitationTemplateKey());
        update.set("summary", meeting.getSummary());
        update.set("location", meeting.getLocation());
        update.set("timeSlots", meeting.getTimeSlots());
        update.set("timeZone", meeting.getTimeZone());
        update.set("locationInfo", meeting.getLocationInfo());
        update.set("title", meeting.getTitle());

        return update;
    }
}
